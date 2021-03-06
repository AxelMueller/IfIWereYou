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
import android.widget.Toast;

import com.ifiwereyou.IfIWereYouApplication;
import com.ifiwereyou.R;
import com.ifiwereyou.network.ChallengeBroadcastReceiver;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.objects.ChallengeState;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

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

        final TextView statusTextView = (TextView) view.findViewById(R.id.challenge_status);
        if (statusTextView != null) {
            challenge.printStatus(statusTextView);
        }

        return view;
    }

    private View newView(Challenge challenge, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        switch (getItemViewType(challenge)) {
            case ViewTypes.NEW_INCOMING_CHALLENGE:
                return newIncomingNewView(inflater, challenge, parent);
            case ViewTypes.OPEN_INCOMING_CHALLENGE:
                return newIncomingOpenView(inflater, challenge, parent);
            case ViewTypes.CLOSED_INCOMING_CHALLENGE:
                return inflater.inflate(R.layout.row_challenge_incoming_closed, parent, false);
            case ViewTypes.OUTGOING_CHALLENGE:
                return inflater.inflate(R.layout.row_challenge_outgoing, parent, false);
            default:
                throw new IllegalArgumentException("Could not find a view for " + getItemViewType(challenge));
        }
    }

    private View newIncomingNewView(LayoutInflater inflater, final Challenge challenge, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.row_challenge_incoming_open, parent, false);
        final LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.buttonContainer);
        if (buttonContainer != null) {
            final Button confirm = (Button) view.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    challenge.accept();
                    sendObjectToParse(challenge);
                    pushAnswerToParse(challenge, ChallengeBroadcastReceiver.ChallengeActions.ACCEPT);
                }
            });
            final Button decline = (Button) view.findViewById(R.id.decline);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    challenge.decline();
                    sendObjectToParse(challenge);
                    pushAnswerToParse(challenge, ChallengeBroadcastReceiver.ChallengeActions.DECLINE);
                }
            });
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int textWidth = view.findViewById(R.id.challenge_content).getMeasuredWidth();
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

    private View newIncomingOpenView(LayoutInflater inflater, final Challenge challenge, ViewGroup parent) {
        final View view = inflater.inflate(R.layout.row_challenge_incoming_open, parent, false);
        final LinearLayout buttonContainer = (LinearLayout) view.findViewById(R.id.buttonContainer);
        if (buttonContainer != null) {
            final Button fulfill = (Button) view.findViewById(R.id.confirm);
            fulfill.setText(R.string.fulfill_challenge);
            fulfill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    challenge.fulfill();
                    sendObjectToParse(challenge);
                    pushAnswerToParse(challenge, ChallengeBroadcastReceiver.ChallengeActions.FULFILL);
                }
            });
            final Button cancel = (Button) view.findViewById(R.id.decline);
            cancel.setText(R.string.cancel_challenge);
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    challenge.cancel();
                    sendObjectToParse(challenge);
                    pushAnswerToParse(challenge, ChallengeBroadcastReceiver.ChallengeActions.CANCEL);
                }
            });
            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int textWidth = view.findViewById(R.id.challenge_content).getMeasuredWidth();
                    int containerWidth = buttonContainer.getMeasuredWidth();

                    if (containerWidth < textWidth) {
                        buttonContainer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        fulfill.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
                        cancel.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.5f));
                    }
                }
            });
        }
        return view;
    }

    private int getItemViewType(Challenge challenge) {
        switch (challenge.getType()) {
            case OUTGOING:
                return ViewTypes.OUTGOING_CHALLENGE;
            case INCOMING:
                return challenge.getChallengeState().getIncomingViewType();
        }
        throw new IllegalArgumentException("Could not find a view type for " + challenge.getType());
    }

    public boolean canCurrentUserSendNewChallenge() {
        for (int i = 0; i < getCount(); i++) {
            Challenge challenge = getItem(i);
            if (challenge.getType() == Challenge.Type.OUTGOING && challenge.getChallengeState().getState() == ChallengeState.state.NEW && (!(challenge.getChallengeState().getState() == ChallengeState.state.ACCEPTED))) {
                return false;
            }
        }
        return true;
    }

    private void sendObjectToParse(ParseObject object) {
        final SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Sending");
        pDialog.setCancelable(false);
        pDialog.show();
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                pDialog.cancel();
                if (e != null) {
                    Toast.makeText(getContext(), R.string.unknownError, Toast.LENGTH_LONG).show();
                }
                loadObjects();
            }
        });
    }

    private void pushAnswerToParse(Challenge challenge, int answer) {
        ParseQuery<ParseInstallation> pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("userID", challenge.getMyOpponent().getObjectId());

        ParsePush push = new ParsePush();
        push.setChannel(IfIWereYouApplication.PARSE_CHANNEL_CHALLENGES);
        push.setQuery(pushQuery);
        push.setData(getJSONDataMessageForPushIntent(answer));
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("Push", "Push successful");
                } else {
                    Log.d("PushException", e.getMessage());
                }
            }
        });
    }

    private JSONObject getJSONDataMessageForPushIntent(int answer) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", ChallengeBroadcastReceiver.ACTION);
            data.put(ChallengeBroadcastReceiver.SENDER_KEY, ParseUser.getCurrentUser().get("firstname") + " " + ParseUser.getCurrentUser().getString("lastname"));
            data.put(ChallengeBroadcastReceiver.SENDER_ID, ParseUser.getCurrentUser().getObjectId());
            data.put(ChallengeBroadcastReceiver.CHALLENGE_ACTION_KEY, answer);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    interface ViewTypes {
        static final int OUTGOING_CHALLENGE = 1;
        static final int NEW_INCOMING_CHALLENGE = 2; // new incoming, user has to decide whether to accept or decline the challenge
        static final int OPEN_INCOMING_CHALLENGE = 3; // accepted, but not completed
        static final int CLOSED_INCOMING_CHALLENGE = 4;
    }

}