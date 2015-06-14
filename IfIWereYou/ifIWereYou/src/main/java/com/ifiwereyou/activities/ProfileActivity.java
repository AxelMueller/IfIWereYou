package com.ifiwereyou.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.ProfileFragmentPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends ActionBarActivity {

    FragmentPagerAdapter mPagerAdapter;
    @InjectView(R.id.profileViewPager)
    ViewPager mViewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mPagerAdapter = new ProfileFragmentPagerAdapter(getSupportFragmentManager(),this);
        ButterKnife.inject(this);
        LoginActivity.setupTabs(mPagerAdapter, mViewPager, getSupportActionBar());
    }

}