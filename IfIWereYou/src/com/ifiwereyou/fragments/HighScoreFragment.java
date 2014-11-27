package com.ifiwereyou.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.provider.ToplistAdapter;

public class HighScoreFragment extends ListFragment {

	public static final String pageTitle = "Highscore"; // Resources.getSystem().getString(R.string.highscorePageTitle);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<Challenge> challengeFlows = SessionData.getDemoUser()
				.getTopChallengesList();
		ArrayAdapter<Challenge> mAdapter = new ToplistAdapter(getActivity(),
				challengeFlows);
		setListAdapter(mAdapter);
		setEmptyText(getActivity().getResources().getString(
				R.string.highscore_master_no_entries));
	}
}