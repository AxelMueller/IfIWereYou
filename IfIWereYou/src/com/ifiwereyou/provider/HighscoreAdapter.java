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
import com.ifiwereyou.objects.User;
import com.ifiwereyou.utils.ContactComperator;

public class HighscoreAdapter extends ArrayAdapter<User> {

	private final Activity context;
	private List<User> contacts;

	static class ViewHolder {
		public TextView nameTextView;
	}

	public HighscoreAdapter(Activity context, List<User> challenges) {
		super(context, android.R.layout.simple_list_item_1, challenges);
		this.context = context;
		this.contacts = challenges;
		Collections.sort(this.contacts, new ContactComperator(User.SCORE));
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
			viewHolder.nameTextView = (TextView) rowView
					.findViewById(android.R.id.text1);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.nameTextView.setText(contacts.get(position).getName());

		return rowView;
	}
}