package com.ifiwereyou.objects;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.ifiwereyou.provider.DataProvider;

public class SessionData {

	// This class could also be realized as subclass from Contact

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
		contactList = new ArrayList<User>();
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
		// if (instance == null)
		// instance = new Profile(userID, lastname, lastname);
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

	public static SessionData getDemoUser(Context context) {
		SessionData session = new SessionData(context, 1, "Simon", "Tenbeitel");
		session.addAllContacts(DataProvider.getFriends(session.context,
				session.getUser()));
		// User hendrik = new User(1, "Hendrik", "Böwer");
		// User axel = new User(2, "Axel", "Müller");
		// User simon = new User(3, "Simon", "Tenbeitel");
		// User christopher = new User(4, "Christopher", "Fries");
		// User christian = new User(5, "Christian", "Düfel");
		// User nils = new User(6, "Nils", "Hirsekorn");
		// User niels = new User(7, "Niels", "Becker");
		// User pascal = new User(8, "Pascal", "Reitermann");
		// User david = new User(9, "David", "Ehlen");
		// User dustin = new User(10, "Dustin", "Hoffner");
		// User heinrich = new User(11, "Heinrich", "Braun");
		// User juergen = new User(12, "Jürgen", "Röthig");
		// User markus = new User(13, "Markus", "Reischl");
		// User daniel = new User(14, "Daniel", "Gerdes");
		// User geraldine = new User(15, "Geraldine", "Beer");
		// session.addContact(hendrik);
		// session.addContact(axel);
		// session.addContact(simon);
		// session.addContact(christopher);
		// session.addContact(christian);
		// session.addContact(nils);
		// session.addContact(niels);
		// session.addContact(pascal);
		// session.addContact(david);
		// session.addContact(dustin);
		// session.addContact(heinrich);
		// session.addContact(juergen);
		// session.addContact(markus);
		// session.addContact(daniel);
		// session.addContact(geraldine);
		// session.addChallengeFlow(new ChallengeFlow(hendrik));
		// session.addChallengeFlow(new ChallengeFlow(axel));
		// session.addChallengeFlow(new ChallengeFlow(simon));
		// session.addChallengeFlow(new ChallengeFlow(christopher));
		// session.addChallengeFlow(new ChallengeFlow(christian));
		// session.addChallengeFlow(new ChallengeFlow(nils));
		// session.addChallengeFlow(new ChallengeFlow(niels));
		// session.addChallengeFlow(new ChallengeFlow(pascal));
		// session.addChallengeFlow(new ChallengeFlow(david));
		// Challenge challenge1 = new Challenge(false, "Singe in der Vorlesung",
		// 1414692168);
		// Challenge challenge2 = new Challenge(
		// false,
		// "Wenn du durch die Stadt gehst, klatsche bei jedem dritten Schritt laut.",
		// 1414693213);
		// Challenge challenge3 = new Challenge(false,
		// "Rülpse so laut es geht!",
		// 1412090413);
		// Challenge challenge4 = new Challenge(false,
		// "Im Schlafanzug zum Bäcker gehen", 1411801675);
		// Challenge challenge5 = new Challenge(true,
		// "Götterspeise-Wettessen in der Einkaufspassage veranstalten",
		// 1414238155);
		// Challenge challenge6 = new Challenge(
		// true,
		// "Mikrofon und Verstärker in die Stadt aufstellen und einen Song trällern",
		// 1414242175);
		// Challenge challenge7 = new Challenge(true,
		// "Im Sommerkleid und Stöckelschuhe durch die Stadt laufen",
		// 1414311781);
		// Challenge challenge8 = new Challenge(true,
		// "Windel anziehen und durch die Gegend laufen", 1414406161);
		// Challenge challenge9 = new Challenge(
		// false,
		// "Ein peinliches Foto machen, ausdrucken und per Brief an die Eltern schicken.",
		// 1414605600);
		// session.getChallengeFlow(hendrik.getContactID()).addChallenge(challenge1);
		// session.getChallengeFlow(axel.getContactID()).addChallenge(challenge2);
		// session.getChallengeFlow(simon.getContactID()).addChallenge(challenge3);
		// session.getChallengeFlow(christopher.getContactID()).addChallenge(
		// challenge4);
		// session.getChallengeFlow(christian.getContactID())
		// .addChallenge(challenge5);
		// session.getChallengeFlow(nils.getContactID()).addChallenge(challenge6);
		// session.getChallengeFlow(niels.getContactID()).addChallenge(challenge7);
		// session.getChallengeFlow(pascal.getContactID()).addChallenge(challenge8);
		// session.getChallengeFlow(david.getContactID()).addChallenge(challenge9);
		return session;
	}
}