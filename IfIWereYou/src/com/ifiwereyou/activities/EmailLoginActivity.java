package com.ifiwereyou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.ifiwereyou.R;

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

	}

	private void register() {
		Intent mIntent = new Intent(this, RegisterActivity.class);
		startActivity(mIntent);
	}
}