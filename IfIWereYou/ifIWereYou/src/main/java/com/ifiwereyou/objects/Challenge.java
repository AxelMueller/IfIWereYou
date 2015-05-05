package com.ifiwereyou.objects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Challenge")
public class Challenge extends ParseObject{

    public static final String KEY_CHALLENGE_TEXT = "challenge_text";
    public static final String KEY_CHALLENGER = "challenger";
    public static final String KEY_CHALLENGED = "challenged";

    public enum Type {
        OUTGOING,
        INCOMING
    }

    public void setChallengeText(String challengeText) {
        put(KEY_CHALLENGE_TEXT, challengeText);
    }

    public String getChallengeText() {
        return getString(KEY_CHALLENGE_TEXT);
    }

    public void setChallenger(ParseUser challenger) {
        put(KEY_CHALLENGER, challenger);
    }

    public ParseUser getChallenger() {
        return getParseUser(KEY_CHALLENGER);
    }

    public void setChallenged(ParseUser challenged) {
        put(KEY_CHALLENGED, challenged);
    }

    public ParseUser getChallenged() {
        return getParseUser(KEY_CHALLENGED);
    }

    public Type getType() {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        return (getChallenger().getObjectId().equals(currentUserId) && !getChallenged().getObjectId().equals(currentUserId)) ? Type.OUTGOING
                : (getChallenged().getObjectId().equals(currentUserId) && !getChallenger().getObjectId().equals(currentUserId)) ? Type.INCOMING
                : null;
    }

    public static ParseQuery<Challenge> getQuery() {
        return ParseQuery.getQuery(Challenge.class);
    }

}