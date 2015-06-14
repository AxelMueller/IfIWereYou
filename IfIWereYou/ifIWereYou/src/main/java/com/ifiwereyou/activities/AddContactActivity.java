package com.ifiwereyou.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.AddFragmentPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AddContactActivity extends ActionBarActivity {

	//  Set ResultOK or ResultCanceled at the end so the MainActivity knows
	// whether to update the view
    FragmentPagerAdapter mPagerAdapter;
    @InjectView(R.id.addViewPager) ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
        mPagerAdapter = new AddFragmentPagerAdapter(getSupportFragmentManager(),this);
        ButterKnife.inject(this);
        LoginActivity.setupTabs(mPagerAdapter, mViewPager, getSupportActionBar());
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Respond to the action bar's Up/Home button
        if(item.getItemId() == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
	}

}