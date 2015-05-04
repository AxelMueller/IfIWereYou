package com.ifiwereyou.provider;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChallengeFlowAdapter extends ArrayAdapter<Challenge> {

	private final Activity context;
	private List<Challenge> challenges;

	static class ViewHolder {
		@InjectView(R.id.row_challenge_master_nameTextView) TextView opponentTextView;
		@InjectView(R.id.row_challenge_master_challengeTextView) TextView challengeTextView;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
	}

	public ChallengeFlowAdapter(Activity context, List<Challenge> challenges) {
		super(context, R.layout.row_challenge_master, challenges);
		this.context = context;
		this.challenges = challenges;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_challenge_master, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder(rowView);
			rowView.setTag(viewHolder);
		}

		// fill data
        final Challenge challenge = challenges.get(position);
		ViewHolder holder = (ViewHolder) rowView.getTag();
        try {
            ParseQuery<ParseUser> opponentDetailsQuery = ParseUser.getQuery();
            ParseUser opponentUser = opponentDetailsQuery.get(challenge.getMyOpponent().getObjectId());
            String opponentName = getParseUserFullName(opponentUser);
            holder.opponentTextView.setText(opponentName);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.opponentTextView.setText("Name not found");
        }
        holder.challengeTextView.setText(challenge.getChallengeText());

		return rowView;
	}

    private static String getParseUserFullName(ParseUser user) {
        return user.get("firstname") + " " + user.get("lastname");
    }

}