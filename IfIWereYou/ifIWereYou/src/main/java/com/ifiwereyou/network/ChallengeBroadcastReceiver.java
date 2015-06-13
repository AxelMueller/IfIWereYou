package com.ifiwereyou.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by D060670 on 18.05.2015.
 */
public class ChallengeBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "com.ifiwereyou.MessagePush";
    public static final String PARSE_JSON_CHANNELS_KEY = "com.parse.Data";
    public static final String PARSE_EXTRA_DATA_KEY = "com.parse.Data";

    public static final String SENDER_KEY = "sender";
    public static final String SENDER_ID = "sender_id";
    public static final String CHALLENGE_TEXT_KEY = "challenge_text";
    public static final String CHALLENGE_ACTION_KEY = "challenge_action";

    public interface ChallengeActions {
        int SEND_NEW = 1;
        int ACCEPT = 2;
        int DECLINE = 3;
        int FULFILL = 4;
        int CANCEL = 5;
        int NEW_FRIEND = 6;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        String channel = extras.getString(PARSE_JSON_CHANNELS_KEY);
        try {
            JSONObject json = new JSONObject(extras.getString(PARSE_EXTRA_DATA_KEY));

            String sender_name = json.getString(SENDER_KEY);
            String sender_id = json.getString(SENDER_ID);

            NotificationHelper notificationHelper = NotificationHelper.getInstance();
            switch (json.getInt(CHALLENGE_ACTION_KEY)) {
                case ChallengeActions.SEND_NEW:
                    String challengeText = json.getString(CHALLENGE_TEXT_KEY);
                    notificationHelper.newChallenge(sender_name, sender_id, challengeText);
                    break;
                case ChallengeActions.ACCEPT:
                    notificationHelper.acceptedChallenge(sender_name, sender_id);
                    break;
                case ChallengeActions.DECLINE:
                    notificationHelper.declinedChallenge(sender_name, sender_id);
                    break;
                case ChallengeActions.FULFILL:
                    notificationHelper.fulfilledChallenge(sender_name, sender_id);
                    break;
                case ChallengeActions.CANCEL:
                    notificationHelper.canceledChallenge(sender_name, sender_id);
                    break;
                case ChallengeActions.NEW_FRIEND:
                    notificationHelper.newFriend(sender_name, sender_id);
                    break;
            }
            Iterator<String> iterator = json.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Log.d("BroadcastReceiver", key + ": " + json.getString(key));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}