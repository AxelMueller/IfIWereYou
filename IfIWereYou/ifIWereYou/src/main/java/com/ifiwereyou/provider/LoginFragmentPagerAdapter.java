package com.ifiwereyou.provider;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.fragments.LoginFragment;
import com.ifiwereyou.fragments.SignupFragment;

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {

    public LoginFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignupFragment();
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
                return LoginFragment.PAGE_TITLE;
            case 1:
                return SignupFragment.PAGE_TITLE;
        }
        return null;
    }

}