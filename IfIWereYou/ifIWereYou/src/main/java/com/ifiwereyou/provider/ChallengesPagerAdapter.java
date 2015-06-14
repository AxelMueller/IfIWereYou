package com.ifiwereyou.provider;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.ChallengeFragment;
import com.ifiwereyou.fragments.FriendStatisticsFragment;

public class ChallengesPagerAdapter extends FragmentPagerAdapter {

    Context context;
    Bundle args;

    public ChallengesPagerAdapter(FragmentManager fm, Context context,Bundle args) {
        super(fm);
        this.context = context;
        this.args = args;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment challengeFragment = new ChallengeFragment();
                challengeFragment.setArguments(args);
                return challengeFragment;
            case 1:
                Fragment friendStatisticsFragment = new FriendStatisticsFragment();
                friendStatisticsFragment.setArguments(args);
                return friendStatisticsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_challenge);
            case 1:
                return context.getString(R.string.title_statistics);
        }
        return null;
    }

}