package com.ifiwereyou.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.objects.User;

public class ServerFunctions {

	private JSONParser jsonParser;

	// Testing in localhost using wamp or xampp
	// use http://10.0.2.2/ to connect to your localhost ie http://localhost/
	private static String loginURL = "http://ifiwereyou.bplaced.net/android_api/";
	private static String registerURL = "http://ifiwereyou.bplaced.net/android_api/";
	private static String addFriendURL = "http://ifiwereyou.bplaced.net/android_api/";
	private static String deleteAccountURL = "http://ifiwereyou.bplaced.net/android_api/";

	private static String login_tag = "login";
	private static String register_tag = "register";
	private static String addFriend_tag = "addfriend";
	private static String deleteAccount_tag = "deleteaccount";

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_ID = "id";
	private static String KEY_FIRSTNAME = "firstname";
	private static String KEY_LASTNAME = "lastname";
	private static String KEY_EMAIL = "mail";
	private static String KEY_PHONENUMBER = "phonenumber";
	private static String KEY_CREATED_AT = "created_at";

	// constructor
	public ServerFunctions() {
		jsonParser = new JSONParser();
	}

	/**
	 * function make Login Request
	 * 
	 * @param email
	 * @param password
	 * @throws Exception
	 * */
	public boolean loginUser(Context context, String email, String password)
			throws Exception {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		try {
			if (json.getString(KEY_SUCCESS) == null)
				return false;
			if (json.getString(KEY_ERROR) != null
					&& json.getString(KEY_ERROR).equals("2")) {
				throw new Exception("Incorrect email or password");
			} else {
				JSONObject json_user = json.getJSONObject("user");
				User user = new User(json.getInt(KEY_ID),
						json_user.getString(KEY_FIRSTNAME),
						json_user.getString(KEY_LASTNAME));
				SessionData.newInstance(context, user);

				DBSQLiteOpenHelper sqlHelper = new DBSQLiteOpenHelper(context);
				SQLiteDatabase db = sqlHelper.getWritableDatabase();

				ContentValues valuesUser = new ContentValues();
				valuesUser.put(DBSQLiteOpenHelper.KEY_ID, user.getContactID());
				valuesUser.put(DBSQLiteOpenHelper.KEY_MAIL,
						json_user.getString(KEY_EMAIL));
				valuesUser.put(DBSQLiteOpenHelper.KEY_FIRST_NAME,
						user.getFirstName());
				valuesUser.put(DBSQLiteOpenHelper.KEY_LAST_NAME,
						user.getLastName());
				valuesUser.put(DBSQLiteOpenHelper.KEY_TIMESTAMP_CREATED,
						System.currentTimeMillis());
				db.insert(DBSQLiteOpenHelper.TABLE_USER, null, valuesUser);
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		// return json
		// Log.e("JSON", json.toString());
	}

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * @throws Exception
	 * */
	public boolean registerUser(Context context, String firstname,
			String lastname, String email, String password, String phoneNumber)
			throws Exception {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair(KEY_FIRSTNAME, firstname));
		params.add(new BasicNameValuePair(KEY_LASTNAME, lastname));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("phonenumber", phoneNumber));

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
		try {
			if (json.getString(KEY_SUCCESS) == null)
				return false;
			if (json.getString(KEY_ERROR) != null
					&& json.getString(KEY_ERROR).equals("2")) {
				throw new Exception("User already existed");
			} else {
				JSONObject json_user = json.getJSONObject("user");
				User user = new User(json.getInt(KEY_ID),
						json_user.getString(KEY_FIRSTNAME),
						json_user.getString(KEY_LASTNAME));
				SessionData.newInstance(context, user);
				return true;
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addFriend(Context context, String email) throws Exception {
		// TODO: Add friend also in contact list on other device.
		// fetch data from server regularly

		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", addFriend_tag));
		params.add(new BasicNameValuePair("userid", SessionData.getInstance()
				.getUser().getContactID()
				+ ""));
		params.add(new BasicNameValuePair("friendmail", email));

		// getting JSON Object
		JSONObject json = jsonParser.getJSONFromUrl(addFriendURL, params);
		try {
			if (json.getString(KEY_SUCCESS) == null) {
				return false;
			} else if (json.getString(KEY_SUCCESS).equals("1")) {
				JSONObject json_user = json.getJSONObject("user");
				User user = new User(Integer.parseInt(json.getString(KEY_ID)),
						json_user.getString(KEY_FIRSTNAME),
						json_user.getString(KEY_LASTNAME));
				DBSQLiteOpenHelper sqlHelper = new DBSQLiteOpenHelper(context);
				SQLiteDatabase db = sqlHelper.getWritableDatabase();

				ContentValues valuesUser = new ContentValues();
				valuesUser.put(DBSQLiteOpenHelper.KEY_ID, user.getContactID());
				valuesUser.put(DBSQLiteOpenHelper.KEY_MAIL,
						json_user.getString(KEY_EMAIL));
				valuesUser.put(DBSQLiteOpenHelper.KEY_FIRST_NAME,
						user.getFirstName());
				valuesUser.put(DBSQLiteOpenHelper.KEY_LAST_NAME,
						user.getLastName());
				valuesUser.put(DBSQLiteOpenHelper.KEY_TIMESTAMP_CREATED,
						System.currentTimeMillis());
				db.insert(DBSQLiteOpenHelper.TABLE_USER, null, valuesUser);

				ContentValues values = new ContentValues();
				values.put(DBSQLiteOpenHelper.KEY_ID1, SessionData
						.getInstance().getUser().getContactID());
				values.put(DBSQLiteOpenHelper.KEY_ID2, user.getContactID());
				values.put(DBSQLiteOpenHelper.KEY_TIMESTAMP,
						System.currentTimeMillis());
				db.insert(DBSQLiteOpenHelper.TABLE_FRIEND, null, values);

				SessionData.getInstance().addContact(user);
				return true;
			}
			if (json.getString(KEY_ERROR) != null
					&& json.getString(KEY_ERROR).equals("3")) {
				throw new Exception("No user with the given mail address");
			}
			throw new Exception();

		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteAccount() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", deleteAccount_tag));
		params.add(new BasicNameValuePair("userid", SessionData.getInstance()
				.getUser().getContactID()
				+ ""));
		JSONObject json = jsonParser.getJSONFromUrl(deleteAccountURL, params);
		try {
			if (json.getString(KEY_SUCCESS) == null) {
				return false;
			} else if (json.getString(KEY_SUCCESS).equals("1")) {
				return SessionData.getInstance().endSession();
			}
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

	}
	// /**
	// * Function get Login status
	// * */
	// public boolean isUserLoggedIn(Context context){
	// DatabaseHandler db = new DatabaseHandler(context);
	// int count = db.getRowCount();
	// if(count > 0){
	// // user logged in
	// return true;
	// }
	// return false;
	// }
	//
	// /**
	// * Function to logout user
	// * Reset Database
	// * */
	// public boolean logoutUser(Context context){
	// DatabaseHandler db = new DatabaseHandler(context);
	// db.resetTables();
	// return true;
	// }

}