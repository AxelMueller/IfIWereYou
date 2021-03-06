package com.ifiwereyou.provider;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.AddContactFragment;
import com.ifiwereyou.fragments.InviteFriendFragment;

public class AddFragmentPagerAdapter extends FragmentPagerAdapter {

    Context context;
    public AddFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
                return context.getResources().getString(R.string.title_add_contact);
            case 1:
                return context.getResources().getString(R.string.title_invite_friend);
        }
        return null;
    }

}