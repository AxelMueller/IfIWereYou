package com.ifiwereyou.objects;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ifiwereyou.provider.DataProvider;

@Deprecated //!!!!!!!!!!!
public class SessionData {

	private static SessionData instance;
	private Context context;
	private User user;
	private List<User> contactList;
	private List<ChallengeFlow> challengeFlows;
	private List<Challenge> topChallengesList;
	private List<User> highscoreList;

	private SessionData(Context context, int userID, String firstname,
			String lastname) {
		this(context, new User(userID, firstname, lastname));
	}

	private SessionData(Context context, User user) {
		super();
		this.context = context;
		this.user = user;
		contactList = DataProvider.getFriends(this.context, user);
		challengeFlows = new ArrayList<ChallengeFlow>();
		topChallengesList = new ArrayList<Challenge>();
		highscoreList = new ArrayList<User>();
	}

	public boolean addContact(User contact) {
		int contactID = contact.getContactID();
		for (User c : contactList) {
			if (contactID == c.getContactID()) {
				if (contact.getFirstName().equals(c.getFirstName())
						&& contact.getLastName().equals(c.getLastName()))
					return false;
				c.setFirstName(contact.getFirstName());
				c.setLastName(contact.getLastName());
				return true;
			}
		}
		return contactList.add(contact);
	}

	public boolean addAllContacts(List<User> contacts) {
		boolean changeInContactList = false;
		for (User c : contacts) {
			if (addContact(c))
				changeInContactList = true;
		}
		return changeInContactList;
	}

	public boolean addChallengeFlow(ChallengeFlow challengeFlow) {
		for (ChallengeFlow cf : challengeFlows) {
			if (cf.getChallenger().getContactID() == challengeFlow
					.getChallenger().getContactID())
				return false;
		}
		return challengeFlows.add(challengeFlow);
	}

	public ChallengeFlow getChallengeFlow(int userID) {
		for (ChallengeFlow cf : challengeFlows) {
			if (userID == cf.getChallenger().getContactID())
				return cf;
		}
		return null;
	}

	public User getUser() {
		return user;
	}

	public List<User> getContactList() {
		return contactList;
	}

	public List<ChallengeFlow> getChallengeFlows() {
		return challengeFlows;
	}

	public List<Challenge> getTopChallengesList() {
		return topChallengesList;
	}

	public List<User> getHighscoreList() {
		return highscoreList;
	}

	public static SessionData getInstance() {
		// TODO: Collect data from Database
		return instance;
	}

	public static SessionData newInstance(Context context, int id,
			String firstname, String lastname) {
		if (instance == null)
			instance = new SessionData(context, id, firstname, lastname);
		return instance;
	}

	public static SessionData newInstance(Context context, User user) {
		if (instance == null)
			instance = new SessionData(context, user);
		return instance;
	}

	public boolean endSession() {
		instance = null;
		return true;
	}
}