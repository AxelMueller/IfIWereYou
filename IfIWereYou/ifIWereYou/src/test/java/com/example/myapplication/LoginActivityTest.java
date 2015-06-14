package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.R;
import com.ifiwereyou.activities.LoginActivity;
import com.ifiwereyou.fragments.LoginFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by D060426 on 08.06.2015.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, manifest = "build/intermediates/manifests/debug/AndroidManifest.xml", resourceDir = "../../../../build/intermediates/res/debug" , emulateSdk = 18, reportSdk = 18)
public class LoginActivityTest {

    private LoginActivity activity;
    private Fragment LoginFragment;
    @Before
    public void setup() {
        activity = Robolectric.buildActivity(LoginActivity.class).create().start().resume().get();
        LoginFragment = new LoginFragment();
        startFragment(LoginFragment, activity);
    }

    @Test
    public void checkNextActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

    @Test
    public void checkFragmentNotNull() throws Exception {
        assertNotNull(LoginFragment);
    }

    @Test
    public void checkLoginErrorToast() throws NullPointerException {

        Button btnLogin = (Button) LoginFragment.getView().findViewById(R.id.loginButton);
        btnLogin.performClick();
        assertThat(ShadowToast.getTextOfLatestToast(), equalTo("Your email address is missing. Please enter it and try again."));
    }


    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }

}
