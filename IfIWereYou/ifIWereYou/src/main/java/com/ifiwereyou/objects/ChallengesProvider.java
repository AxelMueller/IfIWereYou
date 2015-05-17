package com.ifiwereyou.objects;

import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D060426 on 17.05.2015.
 */
//This is the publisher or concrete publisher
public class ChallengesProvider {

    //TODO what about singleton
    private ChallengesProvider challengesProvider;

    private List<Challenge> challenges;

    public ChallengesProvider() {
    }

    public List<Challenge> getAllChallenges() {
        updateChallenges();
        return challenges;
    }

    public List<Challenge> getChallenges() {
        this.challenges = getAllChallenges();
        for (int i = 0; i < challenges.size(); i++) {
            String opponentID = challenges.get(i).getMyOpponent().getObjectId();
            for (int i2 = i + 1; i2 < challenges.size(); i2++) {
                if (opponentID.equals(challenges.get(i2).getMyOpponent().getObjectId())) {
                    challenges.remove(i2);
                    i2--;
                }
            }
        }
        return challenges;
    }

    public void updateChallenges() {
        final ParseUser currentUser = ParseUser.getCurrentUser();

        final ParseQuery<Challenge> query1 = Challenge.getQuery();
        query1.whereEqualTo(Challenge.KEY_CHALLENGER, currentUser);

        final ParseQuery<Challenge> query2 = Challenge.getQuery();
        query2.whereEqualTo(Challenge.KEY_CHALLENGED, currentUser);

        final List<ParseQuery<Challenge>> subQueries = new ArrayList<>();
        subQueries.add(query1);
        subQueries.add(query2);

        ParseQuery<Challenge> query = ParseQuery.or(subQueries);
        query.orderByDescending("createdAt");
        try {
            challenges = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            //TODO improve error handling
//            Toast.makeText(getActivity(), "Error while updating querying for challenges from server", Toast.LENGTH_LONG).show();
        }
    }
}
