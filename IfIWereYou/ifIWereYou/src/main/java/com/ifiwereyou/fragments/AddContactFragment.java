package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.parse.SaveCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddContactFragment extends Fragment {

    public static final String PAGE_TITLE = "Add Contact";

    @InjectView(R.id.add_contact_emailEditText)
    EditText emailEditText;

    public AddContactFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_add_contact,
                container, false);
        ButterKnife.inject(this, rootView);
        return rootView;
    }

    @OnClick(R.id.add_contact_addButton)
    public void add() {
        final String email = emailEditText.getText().toString();

        if(!isValidEmail(email)){
            return;
        }

        final ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("email", email);
        query.findInBackground(new FindCallback<ParseUser>() {
            public void done(List<ParseUser> objects, ParseException e) {
                if (e != null || objects == null || objects.isEmpty()) {
                    outputToastMessage(R.string.add_contact_fail_message, email);
                } else {
                    ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseUser friend = objects.get(0);

                    ParseQuery<ParseUser> friendQuery = getFriendQuery(currentUser, friend);

                    boolean friendshipExists = false;

                    try {
                        friendshipExists = friendQuery.count() > 0;
                        if (!friendshipExists) {
                            ParseQuery<ParseUser> friendQuery2 = getFriendQuery(friend, currentUser);
                            friendshipExists = friendQuery2.count() > 0;
                        }
                    } catch (ParseException e1) {
                        //TODO
                    }

                    if (friendshipExists) {
                        outputToastMessage(R.string.add_contact_already_exists);
                    } else {
                        // the query was successful
                        createFriendship(currentUser, friend);
                        sendPushMessage(currentUser, friend);
                        //go to MainActivity?
                    }
                }
            }
        });
    }

    private void sendPushMessage(ParseUser currentUser, ParseUser friend) {
        ParsePush parsePush = new ParsePush();
        ParseQuery<ParseInstallation> pQuery = ParseInstallation.getQuery();
        pQuery.whereEqualTo("username", friend.getUsername());
        parsePush.setQuery(pQuery);
//                              // set channel later?
        parsePush.setMessage(currentUser.getString("firstname") + " " + currentUser.getString("lastname") + " added you to his contact list.");
        parsePush.sendInBackground();
    }

    private void createFriendship(ParseUser currentUser, ParseUser friend) {
        Friendship friendshipA = new Friendship();
        friendshipA.put("friendA", currentUser);
        friendshipA.put("friendB", friend);
        friendshipA.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    outputToastMessage(R.string.add_contact_success);
                } else {
                    outputToastMessage(R.string.add_contact_failure);
                }
            }
        });
    }

    private ParseQuery<ParseUser> getFriendQuery(ParseUser currentUser, ParseUser friend) {
        ParseQuery<ParseUser> friendQuery = ParseQuery.getQuery("Friendship");
        friendQuery.whereEqualTo("friendA", currentUser);
        friendQuery.whereEqualTo("friendB", friend);
        return friendQuery;
    }

    public boolean isValidEmail(String email) {
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

    public void outputToastMessage(int resId, Object... formatArgs) {
        Toast.makeText(
                getActivity().getApplicationContext(),
                String.format(
                        getString(resId),
                        formatArgs), Toast.LENGTH_LONG).show();
    }
}