Parse.Cloud.define("sendMail", function(request, response) {
var Mandrill = require('mandrill');
Mandrill.initialize('J3rdY0vmtDxqSVFMdwRTUw');

Mandrill.sendEmail({
  message: {
    text: request.params.text,
    subject: request.params.subject,
    from_email: request.params.fromEmail,
    from_name: request.params.fromName,
    to: [
      {
        email: request.params.toEmail
      }
    ]
  },
  async: true
},{
  success: function(httpResponse) {
    console.log(httpResponse);
    response.success("Email sent!");
  },
  error: function(httpResponse) {
    console.error(httpResponse);
    response.error("Uh oh, something went wrong");
  }
});
});