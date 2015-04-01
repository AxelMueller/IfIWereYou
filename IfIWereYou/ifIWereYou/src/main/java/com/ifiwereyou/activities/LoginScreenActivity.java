package com.ifiwereyou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.ifiwereyou.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginScreenActivity extends Activity {

	private static final String TAG = "LoginScreenActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() != null)
            goToMainActivity();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_screen);
        ButterKnife.inject(this);
	}

    @OnClick(R.id.loginButton)
    public void emailLogin() {
        Intent intent = new Intent(LoginScreenActivity.this,
                EmailLoginActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.facebookLogin)
    public void facebookLogin() {
        ParseFacebookUtils.logIn(this, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("If I Were You", "Uh oh. The user cancelled the Facebook login.");
                    return;
                } else if (user.isNew()) {
                    Log.d("If I Were You", "User signed up and logged in through Facebook!");
                } else {
                    Log.d("If I Were You", "User logged in through Facebook!");
                }
                goToMainActivity();
            }
        });
    }

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}