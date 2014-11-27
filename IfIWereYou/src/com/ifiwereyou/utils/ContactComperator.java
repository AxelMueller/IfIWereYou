package com.ifiwereyou.utils;

import java.util.Comparator;

import com.ifiwereyou.objects.User;

public class ContactComperator implements Comparator<User> {

	private int order;

	public ContactComperator(int order) {
		this.order = order;
	}

	@Override
	public int compare(User contact1, User contact2) {
		switch (order) {
		case User.FIRSTNAME:
			return contact1.getFirstName().compareTo(contact2.getFirstName());
		case User.LASTNAME:
			return contact1.getLastName().compareTo(contact2.getLastName());
		case User.SCORE:
			return contact1.getScore() < contact2.getScore() ? -1 : (contact1
					.getScore() == contact2.getScore() ? 0 : 1);
			// Code from Integer.compare. This code had to be copied, because it
			// is not available with this API.
		}
		return 0;
	}
}