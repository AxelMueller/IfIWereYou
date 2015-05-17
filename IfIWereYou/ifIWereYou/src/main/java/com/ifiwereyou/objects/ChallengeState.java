package com.ifiwereyou.objects;

import android.widget.TextView;

/**
 * Created by D060426 on 17.05.2015.
 */
public abstract class ChallengeState {
    public abstract void printStatus(TextView statusTextView);

    public abstract int getIncomingViewType();
}
