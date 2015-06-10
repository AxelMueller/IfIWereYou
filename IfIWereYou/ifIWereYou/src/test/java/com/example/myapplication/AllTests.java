package com.example.myapplication;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.AddContactActivity;
import com.ifiwereyou.activities.ChallengeActivity;
import com.ifiwereyou.activities.LoginActivity;
import com.ifiwereyou.activities.MainActivity;
import com.ifiwereyou.activities.NewChallengeActivity;
import com.ifiwereyou.fragments.AddContactFragment;
import com.ifiwereyou.fragments.LoginFragment;
import com.ifiwereyou.fragments.NewChallengeFragment;

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
public class AllTests {

    private MainActivity activity;
    private LoginActivity loginActivity;
    private AddContactActivity addContactActivity;
    private ChallengeActivity challengeActivity;
    private NewChallengeActivity newChallengeActivity;
    private Fragment addContactFragment;
    private Fragment newChallengeFragment;
    private Fragment loginFragment;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        loginActivity = Robolectric.buildActivity(LoginActivity.class).create().get();
        addContactActivity = Robolectric.buildActivity(AddContactActivity.class).create().get();
        newChallengeActivity = Robolectric.buildActivity(NewChallengeActivity.class).create().get();
        addContactFragment = new AddContactFragment();
        newChallengeFragment = new NewChallengeFragment();
        loginFragment = new LoginFragment();
        startFragment(addContactFragment, addContactActivity);
        startFragment(newChallengeFragment, newChallengeActivity);
        startFragment(loginFragment, loginActivity);
    }

    @Test
    public void checkNextActivityNotNull() throws Exception {
        assertNotNull(loginActivity);
        assertNotNull(activity);
        assertNotNull(newChallengeActivity);
        assertNotNull(addContactActivity);
        assertNotNull(addContactFragment);
        assertNotNull(newChallengeFragment);
    }

    @Test
    public void checkNextActivityNot() throws Exception {
        assertNotNull(loginFragment);
    }

    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }


}
