package com.ifiwereyou.objects;

import android.widget.TextView;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Challenge")
public class Challenge extends ParseObject {

    public static final String KEY_CHALLENGE_TEXT = "challenge_text";
    public static final String KEY_CHALLENGER = "challenger";
    public static final String KEY_CHALLENGED = "challenged";

    String currentUserId = ParseUser.getCurrentUser().getObjectId();
    String challengerObjectId = getParseUserObjectId(getChallenger());
    String challengedObjectId = getParseUserObjectId(getChallenged());

    private ChallengeState challengeState = new NewChallenge();

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

    public ChallengeState getChallengeState(){
        return challengeState;
    }

    public void setChallenged(ParseUser challenged) {
        put(KEY_CHALLENGED, challenged);
    }

    public ParseUser getChallenged() {
        return getParseUser(KEY_CHALLENGED);
    }

    public void accept() {
        if (!(challengeState.getState() == ChallengeState.state.NEW))
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            challengeState = new AcceptedChallenge();
        }
    }

    public void decline() {
        if (!(challengeState.getState() == ChallengeState.state.NEW))
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            challengeState = new DeclinedChallenge();
        }
    }

    public void fulfill() {
        if (!(challengeState.getState() == ChallengeState.state.ACCEPTED))
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            challengeState = new FulfilledChallenge();
        }
    }

    public void cancel() {
        if (!(challengeState.getState() == ChallengeState.state.ACCEPTED))
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            challengeState = new CanceledChallenge();
        }
    }

    public String getParseUserObjectId(ParseUser parseUser){
        return parseUser.getObjectId();
    }

    public boolean currentUserIsChallenger(){
        return challengerObjectId.equals(currentUserId) && !challengedObjectId.equals(currentUserId);
    }

    public boolean currentUserIsChallenged(){
        return challengedObjectId.equals(currentUserId) && !challengerObjectId.equals(currentUserId);
    }

    public ParseUser getMyOpponent() {
        if(currentUserIsChallenger()){
            return (ParseUser) getParseObject(KEY_CHALLENGED);
        } else if(currentUserIsChallenged()){
            return getChallenger();
        }
        return null;
    }

    public Type getType() {
        if(currentUserIsChallenger()){
            return Type.OUTGOING;
        } else {
            if(currentUserIsChallenged()){
                return Type.INCOMING;
            }
            return null;
        }
    }

    public void printStatus(TextView statusTextView) {
        challengeState.printStatus(statusTextView);
    }

    public static ParseQuery<Challenge> getQuery() {
        return ParseQuery.getQuery(Challenge.class);
    }

}