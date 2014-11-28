package com.ifiwereyou.fragments;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.widget.ArrayAdapter;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.SessionData;
import com.ifiwereyou.objects.User;
import com.ifiwereyou.provider.ContactListAdapter;

public class NewChallengeFragment extends ListFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		List<User> contacts = SessionData.getDemoUser(getActivity())
				.getContactList();
		ArrayAdapter<User> mAdapter = new ContactListAdapter(getActivity(),
				contacts);
		setListAdapter(mAdapter);
		getListView().setFastScrollEnabled(true);
		setEmptyText(getActivity().getResources().getString(
				R.string.new_challenge_no_contacts));
	}

}