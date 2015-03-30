package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifiwereyou.R;

public class FeedFragment extends Fragment {

	public static final String pageTitle = "Feed";// Resources.getSystem().getString(R.string.feedPageTitle);

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.add_contact_message,
				container, false);
		return rootView;
	}
}