package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.ServerFunctions;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class AddContactFragment extends Fragment {

    EditText emailEditText;
    Button addButton;

    public AddContactFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contact,
                container, false);
        emailEditText = (EditText) rootView
                .findViewById(R.id.add_contact_emailEditText);
        addButton = (Button) rootView.findViewById(R.id.add_contact_addButton);
        addButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                if (!UserInputCheck.isValidEmail(email)) {
                    Toast.makeText(
                            getActivity().getApplicationContext(),
                            getActivity()
                                    .getResources()
                                    .getString(
                                            R.string.add_contact_email_not_valid_message),
                            Toast.LENGTH_LONG).show();
                    return;
                }

                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("email", email);
                query.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e != null || objects == null || objects.isEmpty()) {
                            Toast.makeText(
                                    getActivity().getApplicationContext(),
                                    String.format(
                                            getString(R.string.add_contact_fail_message),
                                            email), Toast.LENGTH_LONG).show();
                        } else {
                            // the query was successful

                        }
                    }
                });

                ServerFunctions server = new ServerFunctions();
                try {
                    if (server.addFriend(getActivity(), email)) {
                        Toast.makeText(
                                getActivity(),
                                getActivity().getResources().getString(
                                        R.string.add_contact_success),
                                Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    if (e.getMessage() != null
                            && e.getMessage().equals(
                            "No user with the given mail address")) {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                String.format(
                                        getActivity()
                                                .getResources()
                                                .getString(
                                                        R.string.add_contact_fail_message),
                                        email), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(
                                getActivity().getApplicationContext(),
                                getActivity().getResources().getString(
                                        R.string.add_contact_already_exists),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootView;
    }
}