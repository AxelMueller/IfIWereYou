package com.ifiwereyou.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import com.ifiwereyou.R;
import com.ifiwereyou.activities.ChallengeActivity;
import com.ifiwereyou.objects.Friendship;
import com.ifiwereyou.provider.ContactListAdapterParse;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class NewChallengeFragment extends ListFragment {

    public int getFooterViewsCount() {
        return getListView().getFooterViewsCount();
    }

    ParseQueryAdapter.QueryFactory<Friendship> factory;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final List<ParseQuery<Friendship>> queryList = new ArrayList<>();

        ParseUser currentUser = ParseUser.getCurrentUser();

        final ParseQuery<Friendship> contactsQueryA = Friendship.getQuery();
        contactsQueryA.whereEqualTo("friendA", currentUser);
        queryList.add(contactsQueryA);

        final ParseQuery<Friendship> contactsQueryB = Friendship.getQuery();
        contactsQueryB.whereEqualTo("friendB", currentUser);
        queryList.add(contactsQueryB);

        final List<String> columnNames = new ArrayList<String>();

        factory = new ParseQueryAdapter.QueryFactory<Friendship>() {
            @Override
            public ParseQuery<Friendship> create() {
                ParseQuery<Friendship> orStatement = ParseQuery.or(queryList);
                return orStatement;
            }
        };
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactListAdapterParse mAdapter = new ContactListAdapterParse(getActivity(), factory);
        setListAdapter(mAdapter);
        setEmptyText(getActivity().getResources().getString(
                R.string.new_challenge_no_contacts));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        ContactListAdapterParse adapter = (ContactListAdapterParse) getListAdapter();
        Friendship friendship = adapter.getItem(position);
        ParseUser friend = friendship.getFriend();

        Intent intent = new Intent(getActivity(), ChallengeActivity.class);
        intent.putExtra(ChallengeActivity.KEY_OPPONENT, friend.getString("firstname") + " " + friend.getString("lastname"));
        intent.putExtra(ChallengeActivity.KEY_OPPONENT_USER_ID, friend.getObjectId());
        startActivity(intent);

        getActivity().finish();
    }

}