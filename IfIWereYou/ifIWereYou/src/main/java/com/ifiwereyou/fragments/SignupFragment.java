package com.ifiwereyou.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.activities.MainActivity;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by D060336 on 13.04.2015.
 */
public class SignupFragment extends Fragment {

    public static final String PAGE_TITLE = "Sign Up";

    @InjectView(R.id.email)
    EditText emailEditText;
    @InjectView(R.id.password)
    EditText passwordEditText;
    @InjectView(R.id.passwordConfirm)
    EditText passwordConfirmEditText;
    @InjectView(R.id.firstName)
    EditText firstnameEditText;
    @InjectView(R.id.lastName)
    EditText lastnameEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @OnClick(R.id.registerButton)
    public void register() {
        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (firstname.equals("") || lastname.equals("")) {
            Toast.makeText(getActivity(), R.string.registrationNameMissing,
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (email.equals("")) {
            Toast.makeText(getActivity(), R.string.emailMissing, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (!UserInputCheck.isValidEmail(email)) {
            Toast.makeText(
                    getActivity(),
                    getResources().getString(
                            R.string.add_contact_email_not_valid_message),
                    Toast.LENGTH_LONG).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(getActivity(), R.string.passwordMissing, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (!password.equals(passwordConfirmEditText.getText().toString())) {
            Toast.makeText(getActivity(), R.string.passwordNoMatch, Toast.LENGTH_LONG)
                    .show();
            return;
        }

        signUp(email, firstname, lastname, password);
    }

    private void signUp(String email, String firstname, String lastname, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.put("firstname", firstname);
        user.put("lastname", lastname);

        user.signUpInBackground(new SignUpCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    ParseInstallation installation = ParseInstallation.getCurrentInstallation();
                    installation.put("username",ParseUser.getCurrentUser().getUsername());
                    installation.put("userID", ParseUser.getCurrentUser().getObjectId());
                    installation.saveEventually();

                    // Hooray! Let them use the app now.
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    int errorCode = e.getCode();
                    if (errorCode == ParseException.USERNAME_TAKEN || errorCode == ParseException.EMAIL_TAKEN) {
                        Toast.makeText(getActivity(), R.string.emailAlreadyInUse, Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getActivity(), R.string.unknownError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}