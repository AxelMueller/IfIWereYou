package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.ChallengeState;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by D060336 on 13.04.2015.
 */
public class FriendStatisticsFragment extends Fragment {

    @InjectView(R.id.incomingChallenges)
    TextView incomingChallenges;
    @InjectView(R.id.outgoingChallenges)
    TextView outgoingChallenges;
    @InjectView(R.id.fulfilledChallenges)
    TextView fulfilledChallenges;

    public static final String KEY_OPPONENT_USER_ID = "opponent_id";
    ParseUser mOpponent;
    private ParseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friendstatistics, container, false);
        ButterKnife.inject(this, view);
        mOpponent = ParseUser.createWithoutData(ParseUser.class, getArguments().getString(KEY_OPPONENT_USER_ID));
        currentUser = ParseUser.getCurrentUser();
        List<Challenge> challenges = getChallenges();
        createStatistics(challenges);
        return view;
    }

    private void createStatistics(List<Challenge> challenges) {
        int incoming = 0;
        int outgoing = 0;
        int fulfilledMe = 0;
        int fulfilledOpponent = 0;
        for (Challenge challenge : challenges) {
            if (challenge.getChallenged() == currentUser) {
                incoming++;
                ChallengeState.state challengeState = challenge.getChallengeState().getState();
                switch (challengeState) {
                    case FULFILLED:
                        fulfilledMe++;
                        break;
                    default: break;
                }
            } else if(challenge.getChallenged() == mOpponent) {
                outgoing++;
                if (challenge.getChallengeState().getState() == ChallengeState.state.FULFILLED){
                    fulfilledOpponent++;
                }
            }
        }
        incomingChallenges.setText(String.valueOf(incoming));
        outgoingChallenges.setText(String.valueOf(outgoing));
        fulfilledChallenges.setText(String.valueOf(fulfilledMe));
    }

    private List<Challenge> getChallenges() {

        final ParseQuery<Challenge> query1 = Challenge.getQuery();
        query1.whereEqualTo(Challenge.KEY_CHALLENGER, currentUser);

        final ParseQuery<Challenge> query2 = Challenge.getQuery();
        query2.whereEqualTo(Challenge.KEY_CHALLENGED, mOpponent);

        final List<ParseQuery<Challenge>> subQueries = new ArrayList<>();
        subQueries.add(query1);
        subQueries.add(query2);

        ParseQuery<Challenge> query = ParseQuery.or(subQueries);
        List<Challenge> challenges;
        try {
            challenges =  query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error while querying for challenges from server", Toast.LENGTH_LONG).show();
            return null;
        }
        query1.whereEqualTo(Challenge.KEY_CHALLENGER, mOpponent);
        query2.whereEqualTo(Challenge.KEY_CHALLENGED, currentUser);

        query = ParseQuery.or(subQueries);
        try {
            challenges.addAll(query.find());
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error while querying for challenges from server", Toast.LENGTH_LONG).show();
            return null;
        }

        return challenges;
    }
}