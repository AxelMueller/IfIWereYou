package com.ifiwereyou.activities;

/*
 * Copyright 2014 by Axel Müller, Hendrik Böwer, Simon Tenbeitel
 */

// Navigation implemented with: http://developer.android.com/training/implementing-navigation/lateral.html

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.ifiwereyou.R;
import com.ifiwereyou.fragments.ChallengesMasterFragment;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

public class MainActivity extends ActionBarActivity {

    // Request codes for startActivityForResult
    private static final int REQUEST_NEW_CONTACT = 0;

    private static final String TAG_MASTER_FRAGMENT = "master_fragment";

    String userName;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.challenge_master_container, new ChallengesMasterFragment(), TAG_MASTER_FRAGMENT)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        getUserFirstName();
        String userNameToDisplay = userName!=null ? userName : "Can't find username";
        menu.findItem(R.id.action_profile).setTitle(userNameToDisplay);
        return super.onCreateOptionsMenu(menu);
    }

    private void getUserFirstName() {
        // todo refactor
        Object user = ParseUser.getCurrentUser().get("firstname");
        if (user != null) {
            userName = user.toString();
            return;
        }
        if (ParseFacebookUtils.getSession().isOpened())
        {
            Request.executeMeRequestAsync(ParseFacebookUtils.getSession(), new Request.GraphUserCallback()
            {
                @Override
                public void onCompleted(GraphUser user, Response response)
                {
                    if (user != null)
                    {
                        userName = user.getFirstName();
                        ParseUser.getCurrentUser().put("firstname", userName);
                        ParseUser.getCurrentUser().put("lastname", user.getLastName());
                        ParseUser.getCurrentUser().saveInBackground();
                        invalidateOptionsMenu();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_new_challenge:
                intent = new Intent(this, NewChallengeActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_add_contact:
                intent = new Intent(this, AddContactActivity.class);
                startActivityForResult(intent, REQUEST_NEW_CONTACT);
                return true;
            case R.id.action_profile:
                return false;
            case R.id.action_settings:
                return false;
            case R.id.action_delete_account:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Do you really want to delete your account?")
                        .setCancelable(true)
                        .setPositiveButton("Delete",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        try {
                                            ParseUser.getCurrentUser().delete();
                                            Intent mIntent = new Intent(
                                                    MainActivity.this,
                                                    LoginActivity.class);
                                            startActivity(mIntent);
                                            finish();
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                            Toast.makeText(MainActivity.this,
                                                    "Account could not be deleted",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }).setNegativeButton("Cancel", null);
                builder.create().show();
                return true;
            case R.id.action_logout:
                ParseUser.logOut();
                Intent mIntent = new Intent(this, LoginActivity.class);
                startActivity(mIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_NEW_CONTACT:
                if (resultCode == RESULT_OK) {
                    // Update View because a new user was added
                }
                break;

            default:
                break;
        }
    }
}