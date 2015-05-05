package com.ifiwereyou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegisterActivity extends Activity {

    EditText registerMail;
    EditText registerPassword;
    EditText registerPasswordConfirm;
    EditText registerFirstName;
    EditText registerLastName;
    EditText registerPhonenumber;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register);

        registerMail = (EditText) findViewById(R.id.registerEmail);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerPasswordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);
        registerFirstName = (EditText) findViewById(R.id.registerFirstname);
        registerLastName = (EditText) findViewById(R.id.registerLastname);
        registerPhonenumber = (EditText) findViewById(R.id.registerPhonenumber);

        registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String firstname = registerFirstName.getText().toString();
        String lastname = registerLastName.getText().toString();
        String email = registerMail.getText().toString();
        String password = registerPassword.getText().toString();
        if (firstname.equals("") || lastname.equals("")) {
            Toast.makeText(this, R.string.registrationNameMissing,
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (email.equals("")) {
            Toast.makeText(this, R.string.emailMissing, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (!UserInputCheck.isValidEmail(email)) {
            Toast.makeText(
                    getApplicationContext(),
                    getResources().getString(
                            R.string.add_contact_email_not_valid_message),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(this, R.string.passwordMissing, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (!password.equals(registerPasswordConfirm.getText().toString())) {
            Toast.makeText(this, R.string.passwordNoMatch, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        String phoneNumber = registerPhonenumber.getText().toString();

        signUp(email, firstname, lastname, password, phoneNumber);
    }

    private void signUp(String email, String firstname, String lastname, String password, String phone) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);

// other fields can be set just like with ParseObject
        user.put("phone", phone);
        user.put("firstname", firstname);
        user.put("lastname", lastname);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    int errorCode = e.getCode();
                    if (errorCode == ParseException.USERNAME_TAKEN || errorCode == ParseException.EMAIL_TAKEN) {
                        Toast.makeText(RegisterActivity.this, R.string.emailAlreadyInUse, Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(RegisterActivity.this, R.string.unknownError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}