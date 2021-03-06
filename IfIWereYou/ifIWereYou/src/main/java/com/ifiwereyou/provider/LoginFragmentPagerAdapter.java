package com.ifiwereyou.provider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.LoginFragment;
import com.ifiwereyou.fragments.SignupFragment;

public class LoginFragmentPagerAdapter extends FragmentPagerAdapter {

    Context context;
    public LoginFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
                return context.getString(R.string.login);
            case 1:
                return context.getString(R.string.register);
        }
        return null;
    }

}