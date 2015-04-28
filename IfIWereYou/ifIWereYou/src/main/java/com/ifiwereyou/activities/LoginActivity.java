package com.ifiwereyou.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.LoginFragmentPagerAdapter;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by D060336 on 13.04.2015.
 */
public class LoginActivity extends ActionBarActivity{

    FragmentPagerAdapter mPagerAdapter;
    @InjectView(R.id.loginViewPager) ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null)
            goToMainActivity();

        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        setupTabs();
    }

    private void setupTabs() {
        final ActionBar actionBar = getSupportActionBar();

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mPagerAdapter = new LoginFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager
                .setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        // When swiping between pages, select the corresponding
                        // tab. This is necessary because the pages are not
                        // linked to the tabs by default.
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        // Specify that tabs should be displayed in the action bar.
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        actionBar.setIcon(new ColorDrawable(getResources().getColor(
                android.R.color.transparent))); // FIXME: Later this should not be in the source code.

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {

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

    private void goToMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

}