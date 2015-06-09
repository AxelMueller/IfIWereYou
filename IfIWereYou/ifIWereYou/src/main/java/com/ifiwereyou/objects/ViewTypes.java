package com.ifiwereyou.objects;

/**
 * Created by D060426 on 17.05.2015.
 */
public interface ViewTypes {
    static final int OUTGOING_CHALLENGE = 1;
    static final int NEW_INCOMING_CHALLENGE = 2; // new incoming, user has to decide whether to accept or decline the challenge
    static final int OPEN_INCOMING_CHALLENGE = 3; // accepted, but not completed
    static final int CLOSED_INCOMING_CHALLENGE = 4;
}
