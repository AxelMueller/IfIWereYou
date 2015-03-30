package com.ifiwereyou.provider;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ifiwereyou.objects.User;

public class DataProvider {

	public static List<User> getFriends(Context context, User user) {
		List<User> friends = new ArrayList<User>();
		DBSQLiteOpenHelper sqlHelper = new DBSQLiteOpenHelper(context);
		String[] columns = new String[] { DBSQLiteOpenHelper.KEY_ID,
				DBSQLiteOpenHelper.KEY_FIRST_NAME,
				DBSQLiteOpenHelper.KEY_LAST_NAME };
		String selection = DBSQLiteOpenHelper.KEY_ID + " IN(SELECT "
				+ DBSQLiteOpenHelper.KEY_ID1 + " FROM "
				+ DBSQLiteOpenHelper.TABLE_FRIEND + " WHERE "
				+ DBSQLiteOpenHelper.KEY_ID2 + "=?) OR "
				+ DBSQLiteOpenHelper.KEY_ID + " IN(SELECT "
				+ DBSQLiteOpenHelper.KEY_ID2 + " FROM "
				+ DBSQLiteOpenHelper.TABLE_FRIEND + " WHERE "
				+ DBSQLiteOpenHelper.KEY_ID1 + "=?)";
		String[] selectionArgs = new String[] { user.getContactID() + "",
				user.getContactID() + "" };
		SQLiteDatabase db = sqlHelper.getReadableDatabase();
		Cursor cursor = db.query(DBSQLiteOpenHelper.TABLE_USER, columns,
				selection, selectionArgs, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				int id = (Integer.parseInt(cursor.getString(0)));
				String firstName = cursor.getString(1);
				String lastName = cursor.getString(2);
				User contact = new User(id, firstName, lastName);
				friends.add(contact);
			} while (cursor.moveToNext());
		}
		return friends;
	}
}