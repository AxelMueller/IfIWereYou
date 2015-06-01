package com.ifiwereyou.network;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.v4.app.NotificationCompat;

import com.ifiwereyou.IfIWereYouApplication;
import com.ifiwereyou.R;
import com.ifiwereyou.activities.ChallengeActivity;

import java.security.InvalidParameterException;

/**
 * Created by D060670 on 01.06.2015.
 */
public class NotificationHelper {

    private static final int notificationID = 1;

    private static NotificationHelper instance = null;
    private Context mContext;
    private NotificationDB databaseHelper;

    private NotificationHelper() {
        mContext = IfIWereYouApplication.getContext();
        databaseHelper = new NotificationDB(mContext);
    }

    public static synchronized NotificationHelper getInstance() {
        if (null == instance) {
            instance = new NotificationHelper();
        }
        return instance;
    }

    public void buildNotification() {
        Cursor notifications = databaseHelper.getAllNotifications();
        if (1 == notifications.getCount()) {
            notifySingle(notifications);
        } else {
            notifyMulti(notifications);
        }
    }

    private void notifySingle(Cursor notifications) {
        notifications.moveToFirst();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_SENDER_NAME)));
        String notificationText = getNotificationText(notifications.getType(notifications.getColumnIndex(NotificationDB.KEY_NOTIFICATION_TYPE)));
        mBuilder.setContentText(null == notificationText
                ? notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_MESSAGE))
                : notificationText);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, ChallengeActivity.class);
        resultIntent.putExtra(ChallengeActivity.KEY_OPPONENT, notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_SENDER_NAME)));
        resultIntent.putExtra(ChallengeActivity.KEY_OPPONENT_USER_ID, notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_SENDER_ID)));

        // Because clicking the notification opens a new ("special") activity, there's
        // no need to create an artificial back stack.
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        notificationID,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        issueNotification(mBuilder);
    }

    private void notifyMulti(Cursor notifications) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(mContext.getString(R.string.app_name))
                .setContentText(notifications.getCount() + " new messages");

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        // Sets a title for the Inbox in expanded layout
        inboxStyle.setBigContentTitle(mContext.getString(R.string.app_name));

        notifications.moveToFirst();
        do {
            String sender = notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_SENDER_NAME));
            String notificationText = getNotificationText(notifications.getType(notifications.getColumnIndex(NotificationDB.KEY_NOTIFICATION_TYPE)));
            inboxStyle.addLine(sender + ": "
                    + null == notificationText
                        ? notifications.getString(notifications.getColumnIndex(NotificationDB.KEY_MESSAGE))
                        : notificationText);
        } while (notifications.moveToNext());
        mBuilder.setStyle(inboxStyle);
        issueNotification(mBuilder);
    }

    private String getNotificationText(int notificationType) {
        switch (notificationType) {
            case NOTIFICATION_TYPES.NEW_CHALLENGE:
                return null;
            case NOTIFICATION_TYPES.CHALLENGE_ACCEPTED:
                return mContext.getString(R.string.challenge_accepted);
            case NOTIFICATION_TYPES.CHALLENGE_DECLINED:
                return mContext.getString(R.string.challenge_declined);
            case NOTIFICATION_TYPES.CHALLENGE_FULFILLED:
                return mContext.getString(R.string.challenge_fulfilled);
            case NOTIFICATION_TYPES.CHALLENGE_CANCELED:
                return mContext.getString(R.string.challenge_canceled);
            case NOTIFICATION_TYPES.NEW_FRIEND:
                return mContext.getString(R.string.new_fried);
            default:
                throw new InvalidParameterException("Cannot resolve notification type " + notificationType);
        }
    }

    private void issueNotification(NotificationCompat.Builder mBuilder) {
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(notificationID, mBuilder.build());
    }

    private class NotificationDB extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "notification.db";
        private static final int DATABASE_VERSION = 1;

        private static final String TABLE_NOTIFICATION = "notification";
        private static final String KEY_NOTIFICATION_TYPE = "notification_type";
        private static final String KEY_SENDER_NAME = "sender_name";
        private static final String KEY_SENDER_ID = "sender_id";
        private static final String KEY_MESSAGE = "message";

        private NotificationDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NOTIFICATION + "("
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KEY_NOTIFICATION_TYPE + " INTEGER NOT NULL,"
                    + KEY_SENDER_NAME + " TEXT NOT NULL,"
                    + KEY_SENDER_ID + " TEXT NOT NULL,"
                    + KEY_MESSAGE + " TEXT" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        //<editor-fold desc="Insert notification methods">
        private long insertNotification(int type, String sender_name, String sender_id) {
            return insertNotification(type, sender_name, sender_id, null);
        }

        private long insertNotification(int type, String sender_name, String sender_id, String message) {
            ContentValues values = new ContentValues();
            values.put(KEY_NOTIFICATION_TYPE, type);
            values.put(KEY_SENDER_NAME, sender_name);
            values.put(KEY_SENDER_ID, sender_id);
            if (null != message) values.put(KEY_MESSAGE, message);
            return getWritableDatabase().insert(TABLE_NOTIFICATION, null, values);
        }

        private long insertNewChallenge(String sender_name, String sender_id, String message) {
            return insertNotification(NOTIFICATION_TYPES.NEW_CHALLENGE, sender_name, sender_id, message);
        }

        private long insertChallengeAccepted(String sender_name, String sender_id) {
            return insertNotification(NOTIFICATION_TYPES.CHALLENGE_ACCEPTED, sender_name, sender_id);
        }

        private long insertChallengeDeclined(String sender_name, String sender_id) {
            return insertNotification(NOTIFICATION_TYPES.CHALLENGE_DECLINED, sender_name, sender_id);
        }

        private long insertChallengeFulfilled(String sender_name, String sender_id) {
            return insertNotification(NOTIFICATION_TYPES.CHALLENGE_FULFILLED, sender_name, sender_id);
        }

        private long insertChallengeCanceled(String sender_name, String sender_id) {
            return insertNotification(NOTIFICATION_TYPES.CHALLENGE_CANCELED, sender_name, sender_id);
        }

        private long insertNewFriend(String sender_name, String sender_id) {
            return insertNotification(NOTIFICATION_TYPES.NEW_FRIEND, sender_name, sender_id);
        }
        //</editor-fold>

        private Cursor getAllNotifications() {
            String[] columns = new String[]{KEY_NOTIFICATION_TYPE, KEY_SENDER_NAME, KEY_SENDER_ID, KEY_MESSAGE};
            return getReadableDatabase().query(TABLE_NOTIFICATION, columns, null, null, null, null, BaseColumns._ID + " ASC");
        }
    }

    private static interface NOTIFICATION_TYPES {
        int NEW_CHALLENGE = 1;
        int CHALLENGE_ACCEPTED = 2;
        int CHALLENGE_DECLINED = 3;
        int CHALLENGE_FULFILLED = 4;
        int CHALLENGE_CANCELED = 5;
        int NEW_FRIEND = 6;
    }

}