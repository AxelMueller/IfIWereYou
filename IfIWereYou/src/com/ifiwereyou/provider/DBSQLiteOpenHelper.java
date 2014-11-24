package com.ifiwereyou.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBSQLiteOpenHelper extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "FragenDB";

	// Table Names
	private static final String TABLE_USER = "user";
	private static final String TABLE_FRIEND = "friend";
	private static final String TABLE_CHALLENGE = "challenge";
	private static final String TABLE_VOTE = "vote";

	private static final String KEY_ID = "id";
	private static final String KEY_MAIL = "mail";
	private static final String KEY_FACEBOOK_MAIL = "facebookmail";
	private static final String KEY_FIRST_NAME = "firstname";
	private static final String KEY_LAST_NAME = "lastname";
	private static final String KEY_PHONE_NUMBER = "phonenumber";
	private static final String KEY_TIMESTAMP_CREATED = "created";
	private static final String KEY_TIMESTAMP_LAST_CHANGE = "lastchange";
	private static final String KEY_ID1 = "id1";
	private static final String KEY_ID2 = "id2";
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_CHALLENGER_ID = "challenger";
	private static final String KEY_CHALLENGED_ID = "challenged";
	private static final String KEY_TITLE = "title";
	private static final String KEY_TEXT = "text";
	private static final String KEY_COMPLETED = "completed";
	private static final String KEY_TIMESTAMP_STARTED = "started";
	private static final String KEY_TIMESTAMP_FINISHED = "finished";
	private static final String KEY_PRIVACY = "privacy";
	private static final String KEY_CONFIRMED = "confirmed";
	private static final String KEY_CHALLENGE_ID = "challenge";
	private static final String KEY_VOTER_ID = "voter";
	private static final String KEY_TYPE = "type";

	public DBSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY NOT NULL," + KEY_MAIL + " TEXT UNIQUE,"
				+ KEY_FACEBOOK_MAIL + " TEXT UNIQUE," + KEY_FIRST_NAME
				+ " TEXT NOT NULL," + KEY_LAST_NAME + " TEXT NOT NULL,"
				+ KEY_PHONE_NUMBER + " TEXT" + KEY_TIMESTAMP_CREATED
				+ " INTEGER NOT NULL," + KEY_TIMESTAMP_LAST_CHANGE + " INTEGER"
				+ ");";
		db.execSQL(CREATE_USER_TABLE);

		String CREATE_FRIEND_TABLE = "CREATE TABLE " + TABLE_FRIEND + "("
				+ KEY_ID1 + " INTEGER NOT NULL," + KEY_ID2
				+ " INTEGER NOT NULL," + KEY_TIMESTAMP + " INTEGER NOT NULL,"
				+ "PRIMARY KEY(" + KEY_ID1 + "," + KEY_ID2 + "));";
		db.execSQL(CREATE_FRIEND_TABLE);

		String CREATE_CHALLENGE_TABLE = "CREATE TABLE " + TABLE_CHALLENGE + "("
				+ KEY_ID + " INTEGER PRIMARY KEY NOT NULL," + KEY_CHALLENGER_ID
				+ " INTEGER NOT NOLL," + KEY_CHALLENGED_ID
				+ " INTEGER NOT NOLL," + KEY_TITLE + " TEXT_NOT_NULL,"
				+ KEY_TEXT + " TEXT NOT NULL," + KEY_COMPLETED
				+ " INTEGER DEFAULT 0," + KEY_TIMESTAMP_STARTED
				+ " INTEGER NOT NULL," + KEY_TIMESTAMP_FINISHED + " INTEGER,"
				+ KEY_PRIVACY + " INTEGER," + KEY_CONFIRMED
				+ " INTEGER DEFAULT 0";
		db.execSQL(CREATE_CHALLENGE_TABLE);

		String CREATE_VOTE_TABLE = "CREATE TABLE " + TABLE_VOTE + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY NOT NULL," + KEY_CHALLENGE_ID
				+ " INTEGER NOT NOLL," + KEY_VOTER_ID + " INTEGER NOT NOLL,"
				+ KEY_TIMESTAMP + " INTEGER NOT NULL," + KEY_TYPE
				+ " INTEGER NOT NULL" + ");";
		db.execSQL(CREATE_VOTE_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
}