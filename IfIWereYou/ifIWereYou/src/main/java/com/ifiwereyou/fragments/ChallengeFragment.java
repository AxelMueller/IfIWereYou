package com.ifiwereyou.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.provider.ChallengeParseQueryAdapter;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by D060670 on 28.04.2015.
 */
public class ChallengeFragment extends ListFragment {

    public static final String KEY_OPPONENT_USER_ID = "opponent_id";

    @InjectView(R.id.challenge_text_input) EditText challenge_text_input;

    private ChallengeParseQueryAdapter mAdapter;
    private ParseUser mOpponent;

    //<editor-fold desc="Lifecycle methods">
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpponent = ParseUser.createWithoutData(ParseUser.class, getArguments().getString(KEY_OPPONENT_USER_ID));
    }

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ChallengeParseQueryAdapter(getActivity(), mOpponent);
        setListAdapter(mAdapter);
    }
    //</editor-fold>

    @OnClick(R.id.action_send_challenge)
    public void sendChallenge() {
        Challenge challenge = new Challenge();
        challenge.setChallenger(ParseUser.getCurrentUser());
        challenge.setChallenged(mOpponent);
        String challengeText = challenge_text_input.getText().toString();
        challenge.setChallengeText(challengeText);

        challenge.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    challenge_text_input.setText("");
                    mAdapter.loadObjects();
                } else {
                    Toast.makeText(getActivity(), "Error while sending challenge", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}