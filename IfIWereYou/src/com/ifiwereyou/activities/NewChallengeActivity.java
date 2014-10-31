package com.ifiwereyou.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.NewChallengeFragment;

public class NewChallengeActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_challenge);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent))); // FIXME: Later this
														// should probably not
														// be in the source
														// code.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.add(R.id.newChallengeContainer, new NewChallengeFragment())
					.commit();
		}
	}
}