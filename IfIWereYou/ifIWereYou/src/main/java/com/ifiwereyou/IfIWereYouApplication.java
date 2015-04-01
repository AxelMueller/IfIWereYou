package com.ifiwereyou;

import android.app.Application;
import android.content.Context;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by D060670 on 31.03.2015.
 */
public class IfIWereYouApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "uahSAQ1KHf8VbW1gJ1hHcLTbJ3zgZqWO0mpGZRPR", "uiK17aiooWqfnk9J13SBjCz4MduKGsjjsT4tM8mW");
        ParseFacebookUtils.initialize();

        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}