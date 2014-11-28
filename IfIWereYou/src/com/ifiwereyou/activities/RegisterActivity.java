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
		ServerFunctions server = new ServerFunctions();
		String firstname = registerFirstName.getText().toString();
		String lastname = registerLastName.getText().toString();
		String email = registerMail.getText().toString();
		String password = registerPassword.getText().toString();
		if (!password.equals(registerPasswordConfirm.getText().toString()))
			return;
		String phoneNumber = registerPhonenumber.getText().toString();
		if (server.registerUser(this, firstname, lastname, email, password,
				phoneNumber)) {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}