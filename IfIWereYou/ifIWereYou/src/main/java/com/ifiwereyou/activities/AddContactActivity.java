package com.ifiwereyou.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.AddContactFragment;

public class AddContactActivity extends ActionBarActivity {

	// TODO: Set ResultOK or ResultCanceled at the end so the MainActivity knows
	// whether to update the view

	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		getActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent))); // FIXME: Later this
														// should probably not
														// be in the source
														// code.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.addContactContainer, new AddContactFragment())
					.commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Respond to the action bar's Up/Home button
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}