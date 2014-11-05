package com.ifiwereyou.objects;

public class Contact {

	public static final int FIRSTNAME = 0;
	public static final int LASTNAME = 1;
	public static final int SCORE = 2;

	private int contactID; // Name may not be distinct or may change.
	private String firstName;
	private String lastName;
	private int score;

	public Contact(int contactID, String firstName, String lastName) {
		super();
		this.contactID = contactID;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public int getContactID() {
		return contactID;
	}

	public String getFirstName() {
		return firstName;
	}

	public boolean setFirstName(String firstName) {
		this.firstName = firstName;
		return true;
	}

	public String getLastName() {
		return lastName;
	}

	public boolean setLastName(String lastName) {
		this.lastName = lastName;
		return true;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return toString();
	}

	public boolean setContactName(String firstName, String lastName) {
		return setFirstName(firstName) & setLastName(lastName);
	}

	@Override
	public String toString() {
		return firstName + " " + lastName;
	}

}