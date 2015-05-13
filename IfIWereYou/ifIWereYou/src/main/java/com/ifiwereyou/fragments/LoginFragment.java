package com.ifiwereyou.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.ifiwereyou.R;
import com.ifiwereyou.activities.MainActivity;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.HashSet;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by D060336 on 13.04.2015.
 */
public class LoginFragment extends Fragment {

    public static final String PAGE_TITLE = "Log In";

    @InjectView(R.id.username) EditText usernameEditText;
    @InjectView(R.id.password) EditText passwordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @OnClick(R.id.loginButton)
    public void login() {
        String email = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (email.equals("")) {
            Toast.makeText(getActivity(), R.string.emailMissing, Toast.LENGTH_LONG).show();
            return;
        }
        if (password.equals("")) {
            Toast.makeText(getActivity(), R.string.passwordMissing, Toast.LENGTH_LONG).show();
            return;
        }

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    goToMainActivity();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast.makeText(getActivity(), R.string.incorrectLoginData, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @OnClick(R.id.facebookLogin)
    public void facebookLogin() {
        Set<String> permissions = new HashSet(1);
        permissions.add("email");
        ParseFacebookUtils.logIn(permissions, getActivity(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("If I Were You", "Uh oh. The user cancelled the Facebook login.");
                    return;
                } else if (user.isNew()) {
                    Log.d("If I Were You", "User signed up and logged in through Facebook!");
                    initializeNewFacebookAccount();
                } else {
                    Log.d("If I Were You", "User logged in through Facebook!");
                    goToMainActivity();
                }
            }
        });
    }

    private void goToMainActivity() {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("username",ParseUser.getCurrentUser().getUsername());
        installation.put("userID", ParseUser.getCurrentUser().getObjectId());
        installation.saveEventually();

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void initializeNewFacebookAccount() {
        Request request = Request.newMeRequest(ParseFacebookUtils.getSession(), new Request.GraphUserCallback() {
            @Override
            public void onCompleted(final GraphUser user, Response response) {
                Log.d("meRequest", "Me Request complete");
                if (user != null) {
                    final ParseUser currentUser = ParseUser.getCurrentUser();

                    currentUser.put("firstname", user.getFirstName());
                    currentUser.put("lastname", user.getLastName());
                    currentUser.put("email", (String) user.asMap().get("email"));
                    Log.d("email", (String) user.asMap().get("email"));
                    ParseUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Log.d("Save", "Parse user saveInBackground complete");
                            if (e == null) {
                                goToMainActivity();
                            } else if (e.getCode() == ParseException.EMAIL_TAKEN) {
                                Toast.makeText(getActivity(), "You are already registered with that email address", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Log.d("E Code", String.valueOf(e.getCode()));
                                Log.d("E Message", e.getMessage());
                                Toast.makeText(getActivity(), "Login failed, try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(getActivity(), "Login failed, try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        String FIRST_NAME = "first_name";
        String LAST_NAME = "last_name";
        String EMAIL = "email";
        String FIELDS = "fields";
        String REQUEST_FIELDS = TextUtils.join(",", new String[]{
                FIRST_NAME, LAST_NAME, EMAIL
        });

        Bundle parameters = new Bundle();
        parameters.putString(FIELDS, REQUEST_FIELDS);
        request.setParameters(parameters);
        Request.executeBatchAsync(request);
    }

}