package com.ifiwereyou.utils;

import java.util.Comparator;

import com.ifiwereyou.objects.ChallengeFlow;

public class ChallengeFlowComperator implements Comparator<ChallengeFlow> {

	private int order;

	public ChallengeFlowComperator(int order) {
		this.order = order;
	}

	@Override
	public int compare(ChallengeFlow lhs, ChallengeFlow rhs) {
		switch (order) {
		case ChallengeFlow.DATE:
			long challenge1Timestamp = lhs.getLatestChallenge()
					.getTimestampInMillis();
			long challenge2Timestamp = rhs.getLatestChallenge()
					.getTimestampInMillis();
			return challenge1Timestamp < challenge2Timestamp ? -1
					: (challenge1Timestamp == challenge2Timestamp ? 0 : 1);
		}
		return 0;
	}
}