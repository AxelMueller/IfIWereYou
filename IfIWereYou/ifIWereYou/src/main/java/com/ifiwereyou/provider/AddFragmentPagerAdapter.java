package com.ifiwereyou.provider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.fragments.AddContactFragment;
import com.ifiwereyou.fragments.InviteFriendFragment;

public class AddFragmentPagerAdapter extends FragmentPagerAdapter {

    public AddFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AddContactFragment();
            case 1:
                return new InviteFriendFragment();
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
                return AddContactFragment.PAGE_TITLE;
            case 1:
                return InviteFriendFragment.PAGE_TITLE;
        }
        return null;
    }

}