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
public class StatisticsFragment extends Fragment {

    public static final String PAGE_TITLE = "Statistics";

    @InjectView(R.id.allChallenges)
    TextView allChallenges;
    @InjectView(R.id.incomingChallenges)
    TextView incomingChallenges;
    @InjectView(R.id.outgoingChallenges)
    TextView outgoingChallenges;
    @InjectView(R.id.openChallenges)
    TextView openChallenges;
    @InjectView(R.id.acceptedChallenges)
    TextView acceptedChallenges;
    @InjectView(R.id.declinedChallenges)
    TextView declinedChallenges;
    @InjectView(R.id.canceledChallenges)
    TextView canceledChallenges;
    @InjectView(R.id.fulfilledChallenges)
    TextView fulfilledChallenges;

    private ParseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.inject(this, view);
        currentUser = ParseUser.getCurrentUser();
        List<Challenge> challenges = getAllChallenges();
        allChallenges.setText(String.valueOf(challenges.size()));
        challenges = getMyChallenges();
        int incoming = 0;
        int outgoing = 0;
        int open = 0;
        int accepted = 0;
        int declined = 0;
        int canceled = 0;
        int fulfilled = 0;
        for (Challenge challenge : challenges) {
            if (challenge.getChallenged() == currentUser) {
                incoming++;
                ChallengeState.state challengeState = challenge.getChallengeState().getState();
                switch (challengeState) {
                    case NEW:
                        open++;
                        break;
                    case ACCEPTED:
                        accepted++;
                        break;
                    case DECLINED:
                        declined++;
                        break;
                    case CANCELED:
                        canceled++;
                        accepted++;
                        break;
                    case FULFILLED:
                        fulfilled++;
                        accepted++;
                        break;
                    default: break;
                }
            } else {
                outgoing++;
            }
        }
        incomingChallenges.setText(String.valueOf(incoming));
        outgoingChallenges.setText(String.valueOf(outgoing));
        openChallenges.setText(String.valueOf(open));
        acceptedChallenges.setText(String.valueOf(accepted));
        declinedChallenges.setText(String.valueOf(declined));
        canceledChallenges.setText(String.valueOf(canceled));
        fulfilledChallenges.setText(String.valueOf(fulfilled));
        return view;
    }

    private void createStatistics() {

    }

    private List<Challenge> getMyChallenges() {

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
            return query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error while querying for challenges from server", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private List<Challenge> getAllChallenges() {
        final ParseUser currentUser = ParseUser.getCurrentUser();

        final ParseQuery<Challenge> query = Challenge.getQuery();

        try {
            return query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error while querying for challenges from server", Toast.LENGTH_LONG).show();
            return null;
        }
    }
}