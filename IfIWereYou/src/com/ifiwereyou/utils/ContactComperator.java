package com.ifiwereyou.utils;

import java.util.Comparator;

import com.ifiwereyou.objects.Contact;

public class ContactComperator implements Comparator<Contact> {

	private int order;

	public ContactComperator(int order) {
		this.order = order;
	}

	@Override
	public int compare(Contact contact1, Contact contact2) {
		switch (order) {
		case Contact.FIRSTNAME:
			return contact1.getFirstName().compareTo(contact2.getFirstName());
		case Contact.LASTNAME:
			return contact1.getLastName().compareTo(contact2.getLastName());
		case Contact.SCORE:
			return contact1.getScore() < contact2.getScore() ? -1 : (contact1
					.getScore() == contact2.getScore() ? 0 : 1);
			// Code from Integer.compare. This code had to be copied, because it
			// is not available with this API.
		}
		return 0;
	}
}