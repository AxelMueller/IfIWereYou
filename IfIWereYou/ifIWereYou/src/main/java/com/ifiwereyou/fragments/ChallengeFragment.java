package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by D060670 on 28.04.2015.
 */
public class ChallengeFragment extends ListFragment {

    public static final String KEY_OPPONENT_USER_ID = "opponent_id";

    @InjectView(R.id.challenge_text_input) EditText challenge_text_input;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_challenge, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
    }

    @OnClick(R.id.action_send_challenge)
    public void sendChallenge() {
        //todo
        Toast.makeText(getActivity(), "Not supported yet :(", Toast.LENGTH_SHORT).show();
    }

}