package com.ifiwereyou.objects;

import java.util.ArrayList;
import java.util.List;

public class SessionData {

	// This class could also be realized as subclass from Contact

	private static SessionData instance;
	private User user;
	private List<User> contactList;
	private List<ChallengeFlow> challengeFlows;
	private List<Challenge> topChallengesList;
	private List<User> highscoreList;

	private SessionData(int userID, String firstname, String lastname) {
		super();
		this.user = new User(userID, firstname, lastname);
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

	public User getUserAsContact() {
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

	public static SessionData getDemoUser() {
		SessionData user = new SessionData(0, "Max", "Mustermann");
		User hendrik = new User(1, "Hendrik", "Böwer");
		User axel = new User(2, "Axel", "Müller");
		User simon = new User(3, "Simon", "Tenbeitel");
		User christopher = new User(4, "Christopher", "Fries");
		User christian = new User(5, "Christian", "Düfel");
		User nils = new User(6, "Nils", "Hirsekorn");
		User niels = new User(7, "Niels", "Becker");
		User pascal = new User(8, "Pascal", "Reitermann");
		User david = new User(9, "David", "Ehlen");
		User dustin = new User(10, "Dustin", "Hoffner");
		User heinrich = new User(11, "Heinrich", "Braun");
		User juergen = new User(12, "Jürgen", "Röthig");
		User markus = new User(13, "Markus", "Reischl");
		User daniel = new User(14, "Daniel", "Gerdes");
		User geraldine = new User(15, "Geraldine", "Beer");
		user.addContact(hendrik);
		user.addContact(axel);
		user.addContact(simon);
		user.addContact(christopher);
		user.addContact(christian);
		user.addContact(nils);
		user.addContact(niels);
		user.addContact(pascal);
		user.addContact(david);
		user.addContact(dustin);
		user.addContact(heinrich);
		user.addContact(juergen);
		user.addContact(markus);
		user.addContact(daniel);
		user.addContact(geraldine);
		user.addChallengeFlow(new ChallengeFlow(hendrik));
		user.addChallengeFlow(new ChallengeFlow(axel));
		user.addChallengeFlow(new ChallengeFlow(simon));
		user.addChallengeFlow(new ChallengeFlow(christopher));
		user.addChallengeFlow(new ChallengeFlow(christian));
		user.addChallengeFlow(new ChallengeFlow(nils));
		user.addChallengeFlow(new ChallengeFlow(niels));
		user.addChallengeFlow(new ChallengeFlow(pascal));
		user.addChallengeFlow(new ChallengeFlow(david));
		Challenge challenge1 = new Challenge(false, "Singe in der Vorlesung",
				1414692168);
		Challenge challenge2 = new Challenge(
				false,
				"Wenn du durch die Stadt gehst, klatsche bei jedem dritten Schritt laut.",
				1414693213);
		Challenge challenge3 = new Challenge(false, "Rülpse so laut es geht!",
				1412090413);
		Challenge challenge4 = new Challenge(false,
				"Im Schlafanzug zum Bäcker gehen", 1411801675);
		Challenge challenge5 = new Challenge(true,
				"Götterspeise-Wettessen in der Einkaufspassage veranstalten",
				1414238155);
		Challenge challenge6 = new Challenge(
				true,
				"Mikrofon und Verstärker in die Stadt aufstellen und einen Song trällern",
				1414242175);
		Challenge challenge7 = new Challenge(true,
				"Im Sommerkleid und Stöckelschuhe durch die Stadt laufen",
				1414311781);
		Challenge challenge8 = new Challenge(true,
				"Windel anziehen und durch die Gegend laufen", 1414406161);
		Challenge challenge9 = new Challenge(
				false,
				"Ein peinliches Foto machen, ausdrucken und per Brief an die Eltern schicken.",
				1414605600);
		user.getChallengeFlow(hendrik.getContactID()).addChallenge(challenge1);
		user.getChallengeFlow(axel.getContactID()).addChallenge(challenge2);
		user.getChallengeFlow(simon.getContactID()).addChallenge(challenge3);
		user.getChallengeFlow(christopher.getContactID()).addChallenge(
				challenge4);
		user.getChallengeFlow(christian.getContactID())
				.addChallenge(challenge5);
		user.getChallengeFlow(nils.getContactID()).addChallenge(challenge6);
		user.getChallengeFlow(niels.getContactID()).addChallenge(challenge7);
		user.getChallengeFlow(pascal.getContactID()).addChallenge(challenge8);
		user.getChallengeFlow(david.getContactID()).addChallenge(challenge9);
		return user;
	}

}