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
import com.ifiwereyou.objects.Friendship;
import com.ifiwereyou.utils.UserInputCheck;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
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
        final View rootView = inflater.inflate(R.layout.fragment_add_contact,
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

                final ParseQuery<ParseUser> query = ParseUser.getQuery();
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
                            ParseUser currentUser = ParseUser.getCurrentUser();
                            ParseUser friend = objects.get(0);
                            ParseQuery<ParseUser> friendQuery = ParseQuery.getQuery("Friendship");
                            friendQuery.whereEqualTo("friendA", currentUser);
                            friendQuery.whereEqualTo("friendB", friend);

                            boolean friendshipExists = false;

                            try {
                                friendshipExists = friendQuery.count() > 0;
                                if (!friendshipExists) {
                                    ParseQuery<ParseUser> friendQuery2 = ParseQuery.getQuery("Friendship");
                                    friendQuery2.whereEqualTo("friendA", friend);
                                    friendQuery2.whereEqualTo("friendB", currentUser);
                                    friendshipExists = friendQuery2.count() > 0;
                                }
                            } catch (ParseException e1) {
                                //TODO
                            }
                            if (friendshipExists) {
                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        String.format(
                                                getString(R.string.add_contact_already_exists),
                                                email), Toast.LENGTH_LONG).show();
                            } else {
                                // the query was successful
                                Friendship friendshipA = new Friendship();
                                friendshipA.put("friendA", currentUser);
                                friendshipA.put("friendB", friend);
                                friendshipA.saveInBackground();
                                ParsePush parsePush = new ParsePush();
                                ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
                                pQuery.whereEqualTo("username", friend.getUsername());
                                parsePush.setQuery(pQuery);
//                                parsePush.setChannel("");
                                parsePush.setMessage("Endlich angekommen??");
                                parsePush.sendInBackground();

                                Toast.makeText(
                                        getActivity().getApplicationContext(),
                                        String.format(
                                                getString(R.string.add_contact_success),
                                                email), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
            }
        });

        return rootView;
    }
}