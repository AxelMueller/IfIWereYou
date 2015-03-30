package com.ifiwereyou.objects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ifiwereyou.utils.ChallengeComperator;

public class ChallengeFlow {

	public static final int DATE = 0;

	private User challenger;
	private List<Challenge> challenges;

	public ChallengeFlow(int challangerID, String challengerFirstName,
			String challengerLastName) {
		this(new User(challangerID, challengerFirstName, challengerLastName));
	}

	public ChallengeFlow(User challenger) {
		this(challenger, new ArrayList<Challenge>());
	}

	public ChallengeFlow(User challenger, List<Challenge> challenges) {
		super();
		this.challenger = challenger;
		this.challenges = challenges;
		sort(Challenge.DATE);
	}

	public void addChallenge(Challenge challenge) {
		challenges.add(challenge);
		sort(Challenge.DATE);
	}

	// TODO: Delete Challenge

	public User getChallenger() {
		return challenger;
	}

	public Challenge getLatestChallenge() {
		return challenges.get(challenges.size() - 1);
	}

	private void sort(int order) {
		Collections.sort(challenges, new ChallengeComperator(order));
	}

}