package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class InviteFriendFragment extends Fragment {

    public static final String PAGE_TITLE = "Invite Friend";

    @InjectView(R.id.inviteFriend_email_edit_text) EditText emailEditText;
    @InjectView(R.id.inviteFriend_invitationText) EditText invitationText;

    public InviteFriendFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_invite_friend,
                container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.button1)
    public void add(){
        final String email = emailEditText.getText().toString();
        final String invitation = invitationText.getText().toString();

        if(!isValidEmail(email)){
            return;
        }

        ParseUser user = ParseUser.getCurrentUser();
        String userFullName = user.get("firstname")+" "+user.get("lastname");
        sendMail(user.getEmail(), email, userFullName, invitation);
    }

    public boolean isValidEmail(String email){
        if (!UserInputCheck.isValidEmail(email)) {
            Toast.makeText(
                    getActivity().getApplicationContext(),
                    getActivity()
                            .getResources()
                            .getString(
                                    R.string.add_contact_email_not_valid_message),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public void outputToastMessage(int resId, Object...formatArgs ){
        Toast.makeText(
                getActivity().getApplicationContext(),
                String.format(
                        getString(resId),
                        formatArgs), Toast.LENGTH_LONG).show();
    }

    public void sendMail(String senderEmail, String recipientEmail, String fromName, String text) {
        Map<String, String> params = new HashMap<>();
        // use mail of user!!
        params.put("text", text);
        // TODO define string resource
        params.put("subject", "Invitation to use If I Were You");
        params.put("fromEmail", senderEmail);
        //use name
        params.put("fromName", fromName);
        params.put("toEmail", recipientEmail);
        ParseCloud.callFunctionInBackground("sendMail", params, new FunctionCallback<Object>() {
            @Override
            public void done(Object response, ParseException exc) {
                Log.e("cloud code example", "response: " + response);
            }
        });
    }
}