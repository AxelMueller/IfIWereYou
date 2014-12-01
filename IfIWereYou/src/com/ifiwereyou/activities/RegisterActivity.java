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

public class RegisterActivity extends Activity {

	EditText registerMail;
	EditText registerPassword;
	EditText registerPasswordConfirm;
	EditText registerFirstName;
	EditText registerLastName;
	EditText registerPhonenumber;
	Button registerButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);

		registerMail = (EditText) findViewById(R.id.registerEmail);
		registerPassword = (EditText) findViewById(R.id.registerPassword);
		registerPasswordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);
		registerFirstName = (EditText) findViewById(R.id.registerFirstname);
		registerLastName = (EditText) findViewById(R.id.registerLastname);
		registerPhonenumber = (EditText) findViewById(R.id.registerPhonenumber);

		registerButton = (Button) findViewById(R.id.btnRegister);
		registerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				register();
			}
		});
	}

	private void register() {
		String firstname = registerFirstName.getText().toString();
		String lastname = registerLastName.getText().toString();
		String email = registerMail.getText().toString();
		String password = registerPassword.getText().toString();
		if (firstname == null || lastname == null) {
			Toast.makeText(this, R.string.registrationNameMissing,
					Toast.LENGTH_LONG);
			return;
		}
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
		if (!password.equals(registerPasswordConfirm.getText().toString())) {
			Toast.makeText(this, R.string.passwordNoMatch, Toast.LENGTH_LONG)
					.show();
			return;
		}
		String phoneNumber = registerPhonenumber.getText().toString();
		ServerFunctions server = new ServerFunctions();
		try {
			if (server.registerUser(this, firstname, lastname, email, password,
					phoneNumber)) {
				Intent intent = new Intent(this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		} catch (Exception e) {
			if (e.getMessage().equals("User already existed")) {
				Toast.makeText(this, R.string.emailAlreadyInUse,
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, R.string.unknownError, Toast.LENGTH_LONG)
						.show();
			}
		}
	}
}