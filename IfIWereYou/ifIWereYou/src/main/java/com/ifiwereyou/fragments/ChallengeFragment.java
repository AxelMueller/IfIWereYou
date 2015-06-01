package com.ifiwereyou.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.ifiwereyou.IfIWereYouApplication;
import com.ifiwereyou.R;
import com.ifiwereyou.network.ChallengeBroadcastReceiver;
import com.ifiwereyou.objects.Challenge;
import com.ifiwereyou.provider.ChallengeParseQueryAdapter;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SendCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

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
        if (!mAdapter.canCurrentUserSendNewChallenge()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.only_one_outgoing_challenge));
            builder.setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {}
            });
            builder.show();
            return;
        }
        final SweetAlertDialog pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Sending");
        pDialog.setCancelable(false);
        pDialog.show();

        final Challenge challenge = new Challenge();
        challenge.setChallenger(ParseUser.getCurrentUser());
        challenge.setChallenged(mOpponent);
        String challengeText = challenge_text_input.getText().toString();
        challenge.setChallengeText(challengeText);

        challenge.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                pDialog.cancel();
                if (e == null) {
                    challenge_text_input.setText("");
                    mAdapter.loadObjects();
                    pushChallengeToOpponent(challenge);
                } else {
                    Toast.makeText(getActivity(), "Error while sending challenge", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void pushChallengeToOpponent(Challenge challenge) {
        ParseQuery<ParseInstallation> pushQuery = ParseInstallation.getQuery();
        pushQuery.whereEqualTo("userID", getArguments().getString(KEY_OPPONENT_USER_ID));

        ParsePush push = new ParsePush();
        push.setChannel(IfIWereYouApplication.PARSE_CHANNEL_CHALLENGES);
        push.setQuery(pushQuery);
        push.setData(getJSONDataMessageForPushIntent(challenge));
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

    private JSONObject getJSONDataMessageForPushIntent(Challenge challenge) {
        JSONObject data = new JSONObject();
        try {
            data.put("action", ChallengeBroadcastReceiver.ACTION);
            data.put(ChallengeBroadcastReceiver.SENDER_KEY, ParseUser.getCurrentUser().get("firstname") + " " + ParseUser.getCurrentUser().getString("lastname"));
            data.put(ChallengeBroadcastReceiver.SENDER_ID, ParseUser.getCurrentUser().getObjectId());
            data.put(ChallengeBroadcastReceiver.CHALLENGE_ACTION_KEY, ChallengeBroadcastReceiver.ChallengeActions.SEND_NEW);
            data.put(ChallengeBroadcastReceiver.CHALLENGE_TEXT_KEY, challenge.getChallengeText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

}