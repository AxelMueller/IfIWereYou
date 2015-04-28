package com.ifiwereyou.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.R;
import com.ifiwereyou.fragments.ChallengeFragment;

/**
 * Created by D060670 on 28.04.2015.
 */
public class ChallengeActivity extends ActionBarActivity {

    private static final String TAG_CHALLENGE_FRAGMENT = "challenge_fragment";

    public static final String KEY_OPPONENT = "opponent";
    public static final String KEY_OPPONENT_USER_ID = "user_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        Intent callingIntent = getIntent();
        Bundle extras = callingIntent.getExtras();

        setTitle(extras.getString(KEY_OPPONENT, "Opponent"));

        if (savedInstanceState == null) {
            Fragment fragment = new ChallengeFragment();

            Bundle args = new Bundle();
            args.putString(ChallengeFragment.KEY_OPPONENT_USER_ID, extras.getString(KEY_OPPONENT_USER_ID));

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment, TAG_CHALLENGE_FRAGMENT)
                    .commit();

        }

    }

}