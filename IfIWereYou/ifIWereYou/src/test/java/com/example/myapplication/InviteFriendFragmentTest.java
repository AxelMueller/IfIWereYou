package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.R;
import com.ifiwereyou.activities.AddContactActivity;
import com.ifiwereyou.activities.MainActivity;
import com.ifiwereyou.fragments.AddContactFragment;
import com.ifiwereyou.fragments.InviteFriendFragment;

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
public class InviteFriendFragmentTest {

    private MainActivity activity;
    private Fragment inviteFriendFragment;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(MainActivity.class).create()
                //.start()
                //.resume()
                .get();
        inviteFriendFragment = new InviteFriendFragment();
        startFragment(inviteFriendFragment, activity);
    }

    @Test
    public void checkFragmentNotNull() throws Exception {
        assertNotNull(inviteFriendFragment);
    }
    /*
    @Test
    public void checkInviteFriendErrorToast() throws NullPointerException {
        View view = inviteFriendFragment.getView();
        assertNotNull(view); //fails
        //Button btnLogin = (Button) inviteFriendFragment.getView().findViewById(R.id.button1);
        //assertNotNull(btnLogin); //fails
        //btnLogin.performClick();
        //assertThat(ShadowToast.getTextOfLatestToast(), equalTo(inviteFriendFragment.getString(R.string.add_contact_email_not_valid_message)));
    }
    */
    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }

}
