package com.ifiwereyou.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.objects.User;

public class ServerFunctions {

	private JSONParser jsonParser;

	// Testing in localhost using wamp or xampp
	// use http://10.0.2.2/ to connect to your localhost ie http://localhost/
	private static String loginURL = "http://ifiwereyou.bplaced.net/android_api/";
	private static String registerURL = "http://ifiwereyou.bplaced.net/android_api/";

	private static String login_tag = "login";
	private static String register_tag = "register";

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_ID = "id";
	private static String KEY_FIRSTNAME = "firstname";
	private static String KEY_LASTNAME = "lastname";
	private static String KEY_EMAIL = "email";
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
	 * */
	public boolean loginUser(Context context, String email, String password) {
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
		try {
			if (json.getString(KEY_SUCCESS) == null)
				return false;
			else {
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
		// return json
		// Log.e("JSON", json.toString());
	}

	/**
	 * function make Login Request
	 * 
	 * @param name
	 * @param email
	 * @param password
	 * */
	public boolean registerUser(Context context, String firstname,
			String lastname, String email, String password, String phoneNumber) {
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
			else {
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