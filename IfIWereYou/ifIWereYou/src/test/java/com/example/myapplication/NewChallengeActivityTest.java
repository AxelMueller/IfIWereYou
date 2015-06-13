package com.example.myapplication;

import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.NewChallengeActivity;
import com.ifiwereyou.objects.NewChallenge;

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
public class NewChallengeActivityTest {

    private NewChallengeActivity activity;

    @Before
    public void setup() {
        activity = Robolectric.buildActivity(NewChallengeActivity.class).create()
                .start().resume()
                .get();
    }

    @Test
    public void checkNextActivityNotNull() throws Exception {
        assertNotNull(activity);
    }

}
