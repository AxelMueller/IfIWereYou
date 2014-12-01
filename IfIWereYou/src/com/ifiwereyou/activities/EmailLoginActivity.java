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
		if (email == null) {
			Toast.makeText(this, R.string.emailMissing, Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (password == null) {
			Toast.makeText(this, R.string.passwordMissing, Toast.LENGTH_LONG)
					.show();
			return;
		}
		ServerFunctions server = new ServerFunctions();
		if (server.loginUser(this, email, password)) {

		}
	}

	private void register() {
		Intent mIntent = new Intent(this, RegisterActivity.class);
		startActivity(mIntent);
	}
}