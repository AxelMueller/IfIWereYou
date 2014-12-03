package com.ifiwereyou.activities;

/*
 * Copyright 2014 by Axel Müller, Hendrik Böwer, Simon Tenbeitel
 */

// Navigation implemented with: http://developer.android.com/training/implementing-navigation/lateral.html

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.facebook.Session;
import com.ifiwereyou.R;
import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.provider.MainActivityFragmentPagerAdapter;
import com.ifiwereyou.provider.ServerFunctions;

public class MainActivity extends FragmentActivity {

	// Request codes for startActivityForResult
	private static final int REQUEST_NEW_CONTACT = 0;

	FragmentPagerAdapter mPagerAdapter;
	ViewPager mViewPager;
	SessionData profile;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		profile = SessionData.getInstance();

		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getActionBar();

		// ViewPager and its adapters use support library
		// fragments, so use getSupportFragmentManager.
		mViewPager = (ViewPager) findViewById(R.id.mainViewPager);
		mPagerAdapter = new MainActivityFragmentPagerAdapter(
				getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						// When swiping between pages, select the corresponding
						// tab. This is necessary because the pages are not
						// linked to the tabs by default.
						getActionBar().setSelectedNavigationItem(position);
					}
				});

		// Specify that tabs should be displayed in the action bar.
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		actionBar.setIcon(new ColorDrawable(getResources().getColor(
				android.R.color.transparent))); // FIXME: Later this should not
												// be in the source code.

		// Create a tab listener that is called when the user changes tabs.
		ActionBar.TabListener tabListener = new ActionBar.TabListener() {
			@Override
			public void onTabSelected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				// show the given tab
				// When the tab is selected, switch to the
				// corresponding page in the ViewPager.
				mViewPager.setCurrentItem(tab.getPosition());
			}

			@Override
			public void onTabUnselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				// hide the given tab
			}

			@Override
			public void onTabReselected(Tab tab, FragmentTransaction ft) {
				// TODO Auto-generated method stub
				// probably ignore this event
			}
		};

		// Add 4 tabs to the action bar and specify the tab's text and
		// TabListener
		// TODO: Decide whether to use fixed tabs or scrollable tabs:
		// http://developer.android.com/design/building-blocks/tabs.html
		for (int i = 0; i < mPagerAdapter.getCount(); i++) {
			actionBar.addTab(actionBar.newTab()
					.setText(mPagerAdapter.getPageTitle(i))
					.setTabListener(tabListener));
		}

		try {
			Method setHasEmbeddedTabsMethod = actionBar.getClass()
					.getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
			setHasEmbeddedTabsMethod.setAccessible(true);
			setHasEmbeddedTabsMethod.invoke(actionBar, false);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_menu, menu);
		menu.findItem(R.id.action_profile).setTitle(
				profile.getUser().getFirstName());
		return super.onCreateOptionsMenu(menu);
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
			ServerFunctions server = new ServerFunctions();
			if (server.deleteAccount()) {
				Toast.makeText(this, "Account deleted", Toast.LENGTH_LONG)
						.show();
				Intent mIntent = new Intent(this, LoginScreenActivity.class);
				startActivity(mIntent);
				finish();
			} else {
				Toast.makeText(this, "Account could not be deleted",
						Toast.LENGTH_LONG);
			}
		case R.id.action_logout:
			final Session openSession = Session.getActiveSession();
			if (openSession != null) {
				String logout = getResources().getString(
						R.string.com_facebook_loginview_log_out_action);
				String cancel = getResources().getString(
						R.string.com_facebook_loginview_cancel_action);

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Do you really want to log out?")
						.setCancelable(true)
						.setPositiveButton(logout,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										openSession
												.closeAndClearTokenInformation();
										Intent logoutIntent = new Intent(
												MainActivity.this,
												LoginScreenActivity.class);
										startActivity(logoutIntent);
									}
								}).setNegativeButton(cancel, null);
				builder.create().show();
			}
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