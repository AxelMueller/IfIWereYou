package com.ifiwereyou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.LoginFragmentPagerAdapter;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by D060336 on 13.04.2015.
 */
public class LoginActivity extends ActionBarActivity {

    FragmentPagerAdapter mPagerAdapter;
    @InjectView(R.id.loginViewPager)
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() != null)
            goToMainActivity();

        //TODO uncomment!!
//        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
//        parseQuery.whereEqualTo("lastname", "Muelle");
//        ParseUser first = null;
//        parseQuery.findInBackground(new FindCallback<ParseUser>() {
//            @Override
//            public void done(List<ParseUser> parseUsers, ParseException e) {
//                parseUsers.get(0).setPassword("1234");
//            }
//        });
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        mPagerAdapter = new LoginFragmentPagerAdapter(getSupportFragmentManager(),this);
        setupTabs(mPagerAdapter, mViewPager, getSupportActionBar());
    }

    public static void setupTabs(PagerAdapter pagerAdapter, final ViewPager viewPager, final ActionBar actionBar) {

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        viewPager.setAdapter(pagerAdapter);
        viewPager
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

        //TODO: probably uncomment
//        actionBar.setIcon(new ColorDrawable(getResources().getColor(
//                android.R.color.transparent))); // FIXME: Later this should not be in the source code.

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction fragmentTransaction) {
                viewPager.setCurrentItem(tab.getPosition());
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
        //  Decide whether to use fixed tabs or scrollable tabs:
        // http://developer.android.com/design/building-blocks/tabs.html
        for (int i = 0; i < pagerAdapter.getCount(); i++) {
            actionBar.addTab(actionBar.newTab()
                    .setText(pagerAdapter.getPageTitle(i))
                    .setTabListener(tabListener));
        }

        try {
            Method setHasEmbeddedTabsMethod = actionBar.getClass()
                    .getDeclaredMethod("setHasEmbeddedTabs", boolean.class);
            setHasEmbeddedTabsMethod.setAccessible(true);
            setHasEmbeddedTabsMethod.invoke(actionBar, false);
        } catch (Exception e) {
            Log.e("LoginActivity", e.toString());
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