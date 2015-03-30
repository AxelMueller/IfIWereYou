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
import com.ifiwereyou.utils.ChallengeComperator;

public class ToplistAdapter extends ArrayAdapter<Challenge> {

	private final Activity context;
	private List<Challenge> challenges;

	static class ViewHolder {
		public TextView challengeText;
	}

	public ToplistAdapter(Activity context, List<Challenge> challenges) {
		super(context, android.R.layout.simple_list_item_1, challenges);
		this.context = context;
		this.challenges = challenges;
		Collections.sort(this.challenges, new ChallengeComperator(
				Challenge.RATING));
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
			viewHolder.challengeText = (TextView) rowView
					.findViewById(android.R.id.text1);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.challengeText.setText(challenges.get(position).getText());

		return rowView;
	}
}