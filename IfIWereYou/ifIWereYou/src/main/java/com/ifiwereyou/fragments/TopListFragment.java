package com.ifiwereyou.fragments;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.provider.ToplistAdapter;

public class TopListFragment extends ListFragment {

	public static final String pageTitle = "Top List"; // Resources.getSystem().getString(R.string.toplistPageTitle);

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ArrayAdapter<Challenge> mAdapter = new ToplistAdapter(getActivity(),
				new ArrayList<Challenge>());
		setListAdapter(mAdapter);
		setEmptyText(getActivity().getResources().getString(
				R.string.top_challenges_master_no_entries));
	}

}