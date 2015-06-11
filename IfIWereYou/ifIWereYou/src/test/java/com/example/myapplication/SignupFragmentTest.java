package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.LoginActivity;
import com.ifiwereyou.activities.NewChallengeActivity;
import com.ifiwereyou.fragments.NewChallengeFragment;
import com.ifiwereyou.fragments.SignupFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

/**
 * Created by D060426 on 08.06.2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug" , emulateSdk = 18, reportSdk = 18)
public class SignupFragmentTest {

    private LoginActivity activity;
    private Fragment fragment;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(LoginActivity.class).create()
                //.start().resume()
                .get();
        fragment = new SignupFragment();
        startFragment(fragment, activity);
    }

    @Test
    public void checkFragmentNotNull() throws Exception {
        assertNotNull(fragment);
    }

    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }


}

