package com.ifiwereyou.provider;

import com.parse.ParseUser;

/**
 * Created by D060426 on 17.05.2015.
 *
 * This class represents a facade to the challenge subsystem of the If I Were You application
 *
 * probably outdated; check afterwards
 */
public interface ChallengeFacade {

    public void setChallengeText(String challengeText);

    public String getChallengeText();

    public void setChallenger(ParseUser challenger);

    public ParseUser getChallenger();

    public void setChallenged(ParseUser challenged);

    public ParseUser getChallenged();

    public void accept();

    public void decline();

}
