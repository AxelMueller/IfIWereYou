package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by D060336 on 13.04.2015.
 */
public class ProfileFragment extends Fragment {

    public static final String PAGE_TITLE = "@string/profile";

    @InjectView(R.id.emailEdit)
    EditText emailEditText;
    @InjectView(R.id.password)
    EditText passwordEditText;
    @InjectView(R.id.passwordConfirm)
    EditText passwordConfirmEditText;
    @InjectView(R.id.firstName)
    EditText firstnameEditText;
    @InjectView(R.id.lastName)
    EditText lastnameEditText;


    private ParseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        currentUser = ParseUser.getCurrentUser();

        if(currentUser.get("authData") != null && !currentUser.get("authData").equals("")){
            passwordEditText.setVisibility(View.GONE);
            passwordConfirmEditText.setVisibility(View.GONE);
        }
        emailEditText.setText(currentUser.getEmail());
        firstnameEditText.setText((String) currentUser.get("firstname"));
        lastnameEditText.setText((String) currentUser.get("lastname"));
        return view;
    }

    @OnClick(R.id.saveButton)
    public void switchView() {
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
        if (!password.equals(passwordConfirmEditText.getText().toString())) {
            Toast.makeText(getActivity(), R.string.passwordNoMatch, Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (!(email.equals(emailEditText) && firstname.equals(firstnameEditText) && lastname.equals(lastnameEditText) && password.equals(""))) {
            save(email, firstname, lastname, password);
        }
    }

    private void save(String email, String firstname, String lastname, String password) {
        currentUser.setPassword(password);
        currentUser.setEmail(email);
        currentUser.put("firstname", firstname);
        currentUser.put("lastname", lastname);

        currentUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e == null){
                    Toast.makeText(getActivity(), R.string.save_profile, Toast.LENGTH_LONG)
                            .show();
                } else{
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