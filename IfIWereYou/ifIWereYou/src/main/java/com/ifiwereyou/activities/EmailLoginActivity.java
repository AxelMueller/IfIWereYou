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
import com.ifiwereyou.provider.ServerFunctions;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class EmailLoginActivity extends Activity {

	EditText loginEmail;
	EditText loginPassword;
	Button loginButton;
	Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		loginEmail = (EditText) findViewById(R.id.loginEmail);
		loginPassword = (EditText) findViewById(R.id.loginPassword);
		loginButton = (Button) findViewById(R.id.btnLogin);
		registerButton = (Button) findViewById(R.id.btnLinkToRegisterScreen);

		loginButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});

		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	private void login() {
		String email = loginEmail.getText().toString();
		String password = loginPassword.getText().toString();
		if (email.equals("")) {
			Toast.makeText(this, R.string.emailMissing, Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (password.equals("")) {
			Toast.makeText(this, R.string.passwordMissing, Toast.LENGTH_LONG)
					.show();
			return;
		}

        ParseUser.logInInBackground(email, password, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    Intent intent = new Intent(EmailLoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                    Toast.makeText(EmailLoginActivity.this, R.string.incorrectLoginData, Toast.LENGTH_LONG).show();
                }
            }
        });
		/*ServerFunctions server = new ServerFunctions();
		try {
			if (server.loginUser(this, email, password)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (Exception e) {
			if (e.getMessage().equals("Incorrect email or password")) {
				Toast.makeText(this, R.string.incorrectLoginData,
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, R.string.unknownError, Toast.LENGTH_LONG)
						.show();
			}
		}*/
	}

	private void register() {
		Intent mIntent = new Intent(this, RegisterActivity.class);
		startActivity(mIntent);
	}
}