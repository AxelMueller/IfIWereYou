package com.ifiwereyou;

import android.app.Application;
import android.content.Context;

import com.ifiwereyou.activities.MainActivity;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.Friendship;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.PushService;

/**
 * Created by D060670 on 31.03.2015.
 */
public class IfIWereYouApplication extends Application {

    private static final String PARSE_APPLICATION_ID = "uahSAQ1KHf8VbW1gJ1hHcLTbJ3zgZqWO0mpGZRPR";
    private static final String PARSE_CLIENT_KEY = "uiK17aiooWqfnk9J13SBjCz4MduKGsjjsT4tM8mW";

    public static final String PARSE_CHANNEL_CHALLENGES = "challenges_channel";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Parse
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Challenge.class);
        ParseObject.registerSubclass(Friendship.class);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
        ParseFacebookUtils.initialize();

        // Enable Parse to receive push //todo later we might use ParsePushBroadcastReceiver
        PushService.setDefaultPushCallback(this, MainActivity.class);
        PushService.subscribe(this.getApplicationContext(), PARSE_CHANNEL_CHALLENGES, MainActivity.class);

        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

}
