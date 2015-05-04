package com.ifiwereyou.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.activities.ChallengeActivity;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.provider.ChallengeFlowAdapter;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ChallengesMasterFragment extends ListFragment {

	public static final String pageTitle = "Challenges";

    private List<Challenge> mChallenges;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        mChallenges = getChallenges();
		ArrayAdapter<Challenge> mAdapter = new ChallengeFlowAdapter(
				getActivity(), mChallenges);
		setListAdapter(mAdapter);
		setEmptyText(getActivity().getResources().getString(R.string.challenges_master_no_challenges));
	}

    private List<Challenge> getChallenges() {
        List<Challenge> challenges = getAllChallenges();
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

    private List<Challenge> getAllChallenges() {
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
            return query.find();
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error while querying for challenges from server", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), ChallengeActivity.class);

        String opponentUserId = ((Challenge) getListAdapter().getItem(position)).getMyOpponent().getObjectId();
        intent.putExtra(ChallengeActivity.KEY_OPPONENT_USER_ID, opponentUserId);

        try {
            ParseQuery<ParseUser> opponentDetailsQuery = ParseUser.getQuery();
            ParseUser opponentUser = opponentDetailsQuery.get(opponentUserId);
            intent.putExtra(ChallengeActivity.KEY_OPPONENT, opponentUser.getString("firstname") + " " + opponentUser.getString("lastname"));
        } catch (ParseException e) {
            e.printStackTrace();
            intent.putExtra(ChallengeActivity.KEY_OPPONENT, "Name not found");
        }

        startActivity(intent);
    }

}