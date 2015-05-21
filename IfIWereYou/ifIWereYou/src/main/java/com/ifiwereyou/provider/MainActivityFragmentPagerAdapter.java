package com.ifiwereyou.provider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.fragments.ChallengesMasterFragment;

public class MainActivityFragmentPagerAdapter extends FragmentPagerAdapter {

	public MainActivityFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		// case 0:
		// return new FeedFragment();
		case 0:
			return new ChallengesMasterFragment();
//		case 1:
//			return new TopListFragment();
			// case 2:
			// return new HighScoreFragment();
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		// case 0:
		// return FeedFragment.pageTitle;
		case 0:
			return ChallengesMasterFragment.pageTitle;
//		case 1:
//			return TopListFragment.pageTitle;
			// case 2:
			// return HighScoreFragment.pageTitle;
		}
		return null;
	}
}