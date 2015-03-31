package com.ifiwereyou.utils;

import java.util.Comparator;

import com.ifiwereyou.objects.Challenge;

@Deprecated
public class ChallengeComperator implements Comparator<Challenge> {

	private int order;

	public ChallengeComperator(int order) {
		this.order = order;
	}

	@Override
	public int compare(Challenge challenge1, Challenge challenge2) {
		switch (order) {
		case Challenge.DATE:
			long challenge1Timestamp = challenge1.getTimestampInMillis();
			long challenge2Timestamp = challenge2.getTimestampInMillis();
			return challenge1Timestamp < challenge2Timestamp ? -1
					: (challenge1Timestamp == challenge2Timestamp ? 0 : 1);
			// Code from Long.compare. This did not work with current API level.
		case Challenge.RATING:
			return Double.compare(challenge1.getRating(),
					challenge2.getRating());
		}
		return 0;
	}

}