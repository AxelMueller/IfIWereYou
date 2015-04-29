package com.ifiwereyou.provider;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Challenge;

public class ChallengeFlowAdapter extends ArrayAdapter<Challenge> {

	private final Activity context;
	private List<Challenge> challenges;

	static class ViewHolder {
		public TextView challengerTextView;
		public TextView challengeTextView;
	}

	public ChallengeFlowAdapter(Activity context, List<Challenge> challenges) {
		super(context, R.layout.row_challenge_master, challenges);
		this.context = context;
		this.challenges = challenges;
//		Collections.sort(this.challenges, new ChallengeFlowComperator(
//				ChallengeFlow.DATE));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_challenge_master, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.challengerTextView = (TextView) rowView
					.findViewById(R.id.row_challenge_master_nameTextView);
			viewHolder.challengeTextView = (TextView) rowView
					.findViewById(R.id.row_challenge_master_challengeTextView);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
//		holder.challengerTextView.setText(challenges.get(position)
//				.getChallenger().getName());
//		holder.challengeTextView.setText(challenges.get(position)
//				.getLatestChallenge().getText());

		return rowView;
	}
}