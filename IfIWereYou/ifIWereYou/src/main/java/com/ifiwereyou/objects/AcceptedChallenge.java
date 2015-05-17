package com.ifiwereyou.objects;

import android.widget.TextView;

import com.ifiwereyou.R;

/**
 * Created by D060426 on 17.05.2015.
 */
public class AcceptedChallenge extends ChallengeState {
    @Override
    public void printStatus(TextView statusTextView) {
        statusTextView.setText(R.string.accepted);
    }

    @Override
    public int getIncomingViewType() {
        return ViewTypes.OPEN_INCOMING_CHALLENGE;
    }
}
