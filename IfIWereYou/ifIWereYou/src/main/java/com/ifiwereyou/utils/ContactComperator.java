package com.ifiwereyou.utils;

import com.ifiwereyou.objects.User;
import com.parse.ParseUser;

import java.util.Comparator;

@Deprecated
public class ContactComperator implements Comparator<ParseUser> {

    //TODO delete @deprecated later
	private int order;

	public ContactComperator(int order) {
		this.order = order;
	}

	@Override
	public int compare(ParseUser contact1, ParseUser contact2) {
		switch (order) {
		case User.FIRSTNAME:
            return ((String)contact1.get("firstname")).compareTo((String) contact2.get("firstname"));
		case User.LASTNAME:
            return ((String)contact1.get("lastname")).compareTo((String) contact2.get("lastname"));
        //TODO maybe introduce score later
//
//		case User.SCORE:
//			return contact1.getScore() < contact2.getScore() ? -1 : (contact1
//					.getScore() == contact2.getScore() ? 0 : 1);
//			// Code from Integer.compare. This code had to be copied, because it
//			// is not available with this API.
		}
		return 0;
	}
}