package com.example.myapplication;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.ChallengeActivity;
import com.ifiwereyou.fragments.ChallengeFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by D060426 on 08.06.2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug" , emulateSdk = 18, reportSdk = 18)
public class ChallengeActivityTest {

    private ChallengeActivity activity;
    ChallengeFragment challengeFragment;

    @Before
    public void setup() {
        Intent intent = new Intent(RuntimeEnvironment.application, ChallengeActivity.class);
        intent.putExtra(ChallengeActivity.KEY_OPPONENT, "Simon Tenbeitel");
        activity = Robolectric.buildActivity(ChallengeActivity.class).withIntent(intent).create()
                .start()
                .resume()
                .get();
        challengeFragment = new ChallengeFragment();
        startFragment(challengeFragment, activity);
    }

    @Test
    public void checkNextActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }


}
