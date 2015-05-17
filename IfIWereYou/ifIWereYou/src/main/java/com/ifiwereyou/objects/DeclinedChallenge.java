package com.ifiwereyou.objects;

import android.widget.TextView;

import com.ifiwereyou.R;

/**
 * Created by D060426 on 17.05.2015.
 */
public class DeclinedChallenge extends ChallengeState{
    @Override
    public void printStatus(TextView statusTextView) {
        statusTextView.setText(R.string.declined);
    }

    @Override
    public int getIncomingViewType() {
        return ViewTypes.CLOSED_INCOMING_CHALLENGE;
    }
}
