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
import com.ifiwereyou.utils.UserInputCheck;

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
				// Currently no function but a message
				String mailAddress = emailEditText.getText().toString();
				String message = "";
				if (!UserInputCheck.isValidEmail(mailAddress)) {
					message = getActivity().getResources().getString(
							R.string.add_contact_email_not_valid_message);
				} else {
					message = String.format(getActivity().getResources()
							.getString(R.string.add_contact_fail_message),
							mailAddress);
				}
				Toast.makeText(getActivity().getApplicationContext(), message,
						Toast.LENGTH_LONG).show();
			}
		});
		return rootView;
	}
}