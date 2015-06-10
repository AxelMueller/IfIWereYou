package com.example.myapplication;


import com.ifiwereyou.BuildConfig;
import com.ifiwereyou.activities.AddContactActivity;

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
public class AddContactActivityTest {

    private static final String PARSE_APPLICATION_ID = "uahSAQ1KHf8VbW1gJ1hHcLTbJ3zgZqWO0mpGZRPR";
    private static final String PARSE_CLIENT_KEY = "uiK17aiooWqfnk9J13SBjCz4MduKGsjjsT4tM8mW";

    private AddContactActivity addContactActivity;

    @Before
    public void setup() {
        addContactActivity = Robolectric.buildActivity(AddContactActivity.class).create().get();
    }

    @Test
    public void checkNextActivityNotNull() throws Exception {
        assertNotNull(addContactActivity);
    }
}
