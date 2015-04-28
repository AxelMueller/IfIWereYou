package com.ifiwereyou;

import android.app.Application;
import android.content.Context;

import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.Friendship;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

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
        ParseObject.registerSubclass(Challenge.class);
        ParseObject.registerSubclass(Friendship.class);
        Parse.initialize(this, "uahSAQ1KHf8VbW1gJ1hHcLTbJ3zgZqWO0mpGZRPR", "uiK17aiooWqfnk9J13SBjCz4MduKGsjjsT4tM8mW");
        ParseFacebookUtils.initialize();

        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}