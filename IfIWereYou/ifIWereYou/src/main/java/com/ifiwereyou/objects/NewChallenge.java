package com.ifiwereyou.objects;

import android.widget.TextView;

import com.ifiwereyou.R;

/**
 * Created by D060426 on 17.05.2015.
 */
public class NewChallenge extends ChallengeState {
    @Override
    public void printStatus(TextView statusTextView) {
        statusTextView.setText(R.string.no_answer_yet);
    }

    @Override
    public int getIncomingViewType() {
        return ViewTypes.NEW_INCOMING_CHALLENGE;
    }

    @Override
    public state getState() {
        return state.NEW;
    }
}
