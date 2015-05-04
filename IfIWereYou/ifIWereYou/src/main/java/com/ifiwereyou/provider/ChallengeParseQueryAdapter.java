package com.ifiwereyou.provider;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by D060670 on 28.04.2015.
 */
public class ChallengeParseQueryAdapter extends ParseQueryAdapter<Challenge> {

    public ChallengeParseQueryAdapter(Context context, final ParseUser otherUser) {
        super(context, new QueryFactory<Challenge>() {
            @Override
            public ParseQuery<Challenge> create() {
                ParseQuery<Challenge> sub_query1 = Challenge.getQuery();
                sub_query1.whereEqualTo(Challenge.KEY_CHALLENGER, otherUser);
                sub_query1.whereEqualTo(Challenge.KEY_CHALLENGED, ParseUser.getCurrentUser());

                ParseQuery<Challenge> sub_query2 = Challenge.getQuery();
                sub_query2.whereEqualTo(Challenge.KEY_CHALLENGER, ParseUser.getCurrentUser());
                sub_query2.whereEqualTo(Challenge.KEY_CHALLENGED, otherUser);

                List<ParseQuery<Challenge>> or_queries = new ArrayList<ParseQuery<Challenge>>(2);
                or_queries.add(sub_query1);
                or_queries.add(sub_query2);
                ParseQuery<Challenge> query = ParseQuery.or(or_queries);
                query.orderByAscending("createdAt");

                return query;
            }
        });
    }

    @Override
    public View getItemView(Challenge challenge, View v, ViewGroup parent) {
        View view = newView(challenge, parent);

        final TextView challengeTextView = (TextView) view.findViewById(R.id.challenge_content);
        challengeTextView.setText(challenge.getChallengeText());
        final LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.buttonContainer);
        if (buttonContainer != null) {
            final Button confirm = (Button) view.findViewById(R.id.confirm);
            final Button decline = (Button) view.findViewById(R.id.decline);
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int textWidth = challengeTextView.getMeasuredWidth();
                    int containerWidth = buttonContainer.getMeasuredWidth();

                    if (containerWidth < textWidth) {
                        buttonContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        confirm.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
                        decline.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
                    }
                }
            });

        }

        return view;
    }

    private View newView(Challenge challenge, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        switch (getItemViewType(challenge)) {
            case ViewTypes.INCOMING_CHALLENGE:
                return inflater.inflate(R.layout.row_challenge_bubble_left, parent, false);
            case ViewTypes.OUTGOING_CHALLENGE:
                return inflater.inflate(R.layout.row_challenge_bubble_right, parent, false);
            default:
                throw new IllegalArgumentException("Could not find a view for " + getItemViewType(challenge));
        }
    }

    private int getItemViewType(Challenge challenge) {
        switch (challenge.getType()) {
            case OUTGOING:
                return ViewTypes.OUTGOING_CHALLENGE;
            case INCOMING:
                return ViewTypes.INCOMING_CHALLENGE;
            default:
                throw new IllegalArgumentException("Could not find a view type for " + challenge.getType());
        }
    }

    interface ViewTypes {
        static final int OUTGOING_CHALLENGE = 1;
        static final int INCOMING_CHALLENGE = 2;
    }

}