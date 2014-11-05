package com.ifiwereyou.objects;

import java.util.ArrayList;
import java.util.List;

public class User {

	// This class could also be realized as subclass from Contact

	private static User instance;
	private Contact user;
	private List<Contact> contactList;
	private List<ChallengeFlow> challengeFlows;
	private List<Challenge> topChallengesList;
	private List<Contact> highscoreList;

	private User(int userID, String firstname, String lastname) {
		super();
		this.user = new Contact(userID, firstname, lastname);
		contactList = new ArrayList<Contact>();
		challengeFlows = new ArrayList<ChallengeFlow>();
		topChallengesList = new ArrayList<Challenge>();
		highscoreList = new ArrayList<Contact>();
	}

	public boolean addContact(Contact contact) {
		int contactID = contact.getContactID();
		for (Contact c : contactList) {
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

	public boolean addAllContacts(List<Contact> contacts) {
		boolean changeInContactList = false;
		for (Contact c : contacts) {
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

	public Contact getUserAsContact() {
		return user;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public List<ChallengeFlow> getChallengeFlows() {
		return challengeFlows;
	}

	public List<Challenge> getTopChallengesList() {
		return topChallengesList;
	}

	public List<Contact> getHighscoreList() {
		return highscoreList;
	}

	public static User getInstance() {
		// TODO: Collect data from Database
		// if (instance == null)
		// instance = new Profile(userID, lastname, lastname);
		return instance;
	}

	public static User getDemoUser() {
		User user = new User(0, "Max", "Mustermann");
		Contact hendrik = new Contact(1, "Hendrik", "Böwer");
		Contact axel = new Contact(2, "Axel", "Müller");
		Contact simon = new Contact(3, "Simon", "Tenbeitel");
		Contact christopher = new Contact(4, "Christopher", "Fries");
		Contact christian = new Contact(5, "Christian", "Düfel");
		Contact nils = new Contact(6, "Nils", "Hirsekorn");
		Contact niels = new Contact(7, "Niels", "Becker");
		Contact pascal = new Contact(8, "Pascal", "Reitermann");
		Contact david = new Contact(9, "David", "Ehlen");
		Contact dustin = new Contact(10, "Dustin", "Hoffner");
		Contact heinrich = new Contact(11, "Heinrich", "Braun");
		Contact juergen = new Contact(12, "Jürgen", "Röthig");
		Contact markus = new Contact(13, "Markus", "Reischl");
		Contact daniel = new Contact(14, "Daniel", "Gerdes");
		Contact geraldine = new Contact(15, "Geraldine", "Beer");
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