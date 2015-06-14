package com.ifiwereyou.provider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.ProfileFragment;
import com.ifiwereyou.fragments.StatisticsFragment;

public class ProfileFragmentPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public ProfileFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ProfileFragment();
            case 1:
                return new StatisticsFragment();
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
                return context.getString(R.string.title_profile);
            case 1:
                return context.getString(R.string.title_statistics);
        }
        return null;
    }

}