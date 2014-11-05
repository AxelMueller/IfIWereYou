package com.ifiwereyou.objects;

public class Challenge {

	public static final int DATE = 0;
	public static final int RATING = 1;

	private boolean incoming;
	private String text;
	private long timestampInMillis;
	private static final int maxRating = 5;
	private double rating; // Overall rating on server
	private int ratingCount;
	private double userRating = -1; // negative if the user has not voted for
									// this challenge yet.

	public Challenge(boolean incoming, String text, long timestampInMillis) {
		super();
		this.incoming = incoming;
		this.text = text;
		this.timestampInMillis = timestampInMillis;
	}

	public String getText() {
		return text;
	}

	public boolean isIncoming() {
		return incoming;
	}

	public boolean isOutgoing() {
		return !incoming;
	}

	public long getTimestampInMillis() {
		return timestampInMillis;
	}

	public boolean rate(int rate) {
		if (rate < 0 || rate > maxRating) {
			return false;
		}
		// TODO: Work with server as soon as available
		rating = (rating * ratingCount + rate) / ratingCount + 1;
		ratingCount++;
		return true;
	}

	public double getRating() {
		return rating;
	}
}