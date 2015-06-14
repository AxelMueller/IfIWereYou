package com.ifiwereyou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.ChallengeFragment;
import com.ifiwereyou.network.NotificationHelper;
import com.ifiwereyou.provider.ChallengesPagerAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by D060670 on 28.04.2015.
 */
public class ChallengeActivity extends ActionBarActivity {

    private static final String TAG_CHALLENGE_FRAGMENT = "challenge_fragment";

    public static final String KEY_OPPONENT = "opponent";
    public static final String KEY_OPPONENT_USER_ID = "user_id";

    FragmentPagerAdapter mPagerAdapter;
    @InjectView(R.id.challengeViewPager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        Intent callingIntent = getIntent();
        Bundle extras = callingIntent.getExtras();

        setTitle(extras.getString(KEY_OPPONENT, "Opponent"));
        Bundle args = new Bundle();
        if (savedInstanceState == null) {

            args.putString(ChallengeFragment.KEY_OPPONENT_USER_ID, extras.getString(KEY_OPPONENT_USER_ID));
        }
        mPagerAdapter = new ChallengesPagerAdapter(getSupportFragmentManager(),this, args);
        ButterKnife.inject(this);
        LoginActivity.setupTabs(mPagerAdapter, mViewPager, getSupportActionBar());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread dismissNotificationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                NotificationHelper.getInstance().dismissSingle(getIntent().getExtras().getString(KEY_OPPONENT_USER_ID));
            }
        });
        dismissNotificationThread.start();
    }

}