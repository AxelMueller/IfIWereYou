package com.ifiwereyou.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.ChallengeFlow;
import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.provider.ChallengeFlowAdapter;

public class ChallengesMasterFragment extends ListFragment {

	public static final String pageTitle = "Challenges"; // Resources.getString(R.string.feedPageTitle);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<ChallengeFlow> challengeFlows = SessionData.getDemoUser(
				getActivity()).getChallengeFlows();
		ArrayAdapter<ChallengeFlow> mAdapter = new ChallengeFlowAdapter(
				getActivity(), challengeFlows);
		setListAdapter(mAdapter);
		setEmptyText(getActivity().getResources().getString(
				R.string.challenges_master_no_challenges));
	}

}