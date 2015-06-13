package com.example.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.AddContactActivity;
import com.ifiwereyou.fragments.AddContactFragment;

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
public class AddContactFragmentTest {

    private AddContactActivity addContactActivity;
    private Fragment addContactFragment;

    @Before
    public void setup() {
        addContactActivity = Robolectric.buildActivity(AddContactActivity.class).create()
                .start().resume()
                .get();
        addContactFragment = new AddContactFragment();
        startFragment(addContactFragment, addContactActivity);
    }

    @Test
    public void checkFragmentNotNull() throws Exception {
        assertNotNull(addContactFragment);
    }



    public void startFragment(Fragment fragment, ActionBarActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(fragment,null);
        fragmentTransaction.commit();
    }


}

