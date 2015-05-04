package com.ifiwereyou.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.provider.ChallengeFlowAdapter;

public class ChallengesMasterFragment extends ListFragment {

	public static final String pageTitle = "Challenges";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		List<ChallengeFlow> challengeFlows = SessionData.getInstance()
//				.getChallengeFlows();
		ArrayAdapter<Challenge> mAdapter = new ChallengeFlowAdapter(
				getActivity(), new ArrayList<Challenge>() {
        });
		setListAdapter(mAdapter);
		setEmptyText(getActivity().getResources().getString(R.string.challenges_master_no_challenges));
	}

}