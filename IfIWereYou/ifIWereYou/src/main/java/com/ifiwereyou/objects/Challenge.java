package com.ifiwereyou.objects;

import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.provider.ChallengeFacade;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Challenge")
public class Challenge extends ParseObject implements ChallengeFacade{

    public static final String KEY_CHALLENGE_TEXT = "challenge_text";
    public static final String KEY_CHALLENGER = "challenger";
    public static final String KEY_CHALLENGED = "challenged";
    public static final String KEY_ACCEPTED = "accepted";
    public static final String KEY_DECLINED = "declined";
    public static final String KEY_FULFILLED = "fulfilled";
    public static final String KEY_CANCELED = "canceled";

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

    public void accept() {
        if (!isNew())
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            put(KEY_ACCEPTED, true);
            put(KEY_DECLINED, false);
        }
    }

    public void decline() {
        if (!isNew())
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            put(KEY_DECLINED, true);
            put(KEY_ACCEPTED, false);
        }
    }

    public boolean isAccepted() {
        return getBoolean(KEY_ACCEPTED);
    }

    public boolean isDeclined() {
        return getBoolean(KEY_DECLINED);
    }

    // new challenge with no answer from opponent yet (accept or decline)
    public boolean isNew() {
        return (!getBoolean(KEY_ACCEPTED)) && (!getBoolean(KEY_DECLINED));
    }

    public void fulfill() {
        if (!isAccepted())
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            put(KEY_FULFILLED, true);
            put(KEY_CANCELED, false);
        }
    }

    public void cancel() {
        if (!isAccepted())
            return;
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        if (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) {
            put(KEY_CANCELED, true);
            put(KEY_FULFILLED, false);
        }
    }

    public boolean isFulfilled() {
        return getBoolean(KEY_FULFILLED);
    }

    public boolean isCancelled() {
        return getBoolean(KEY_CANCELED);
    }

    public boolean isOpen() {
        return isAccepted() && (!isFulfilled()) && (!isCancelled());
    }

    public ParseUser getMyOpponent() {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        return (getParseObject(KEY_CHALLENGER).getObjectId().equals(currentUserId) && !getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId)) ? (ParseUser) getParseObject(KEY_CHALLENGED)
                : (getParseObject(KEY_CHALLENGED).getObjectId().equals(currentUserId) && !getParseObject(KEY_CHALLENGER).getObjectId().equals(currentUserId)) ? (ParseUser) getParseObject(KEY_CHALLENGER)
                : null;
    }

    //differentiating between incoming and outgoing challenges
    public Type getType() {
        String currentUserId = ParseUser.getCurrentUser().getObjectId();
        return (getChallenger().getObjectId().equals(currentUserId) && !getChallenged().getObjectId().equals(currentUserId)) ? Type.OUTGOING
                : (getChallenged().getObjectId().equals(currentUserId) && !getChallenger().getObjectId().equals(currentUserId)) ? Type.INCOMING
                : null;
    }

    public void printStatus(TextView statusTextView) {
        if (isNew()) {
            statusTextView.setText(R.string.no_answer_yet);
        } else if (isDeclined()) {
            statusTextView.setText(R.string.declined);
        } else if (isOpen()) {
            statusTextView.setText(R.string.accepted);
        } else {
            if (isFulfilled()) {
                statusTextView.setText(R.string.fulfilled);
            } else if (isCancelled()) {
                statusTextView.setText(R.string.cancelled);
            }
        }
    }

    public static ParseQuery<Challenge> getQuery() {
        return ParseQuery.getQuery(Challenge.class);
    }

}