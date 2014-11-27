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

public class ContactListAdapter extends ArrayAdapter<User> {

	private final Activity context;
	private List<User> contacts;

	static class ViewHolder {
		public TextView contactNameTextView;
	}

	public ContactListAdapter(Activity context, List<User> contacts) {
		super(context, R.layout.row_contact_list, contacts);
		this.context = context;
		this.contacts = contacts;
		Collections.sort(this.contacts,
				new ContactComperator(User.FIRSTNAME));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		// reuse views
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.row_contact_list, null);
			// configure view holder
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.contactNameTextView = (TextView) rowView
					.findViewById(R.id.row_contact_list_contactNameTextView);
			rowView.setTag(viewHolder);
		}

		// fill data
		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.contactNameTextView.setText(contacts.get(position)
				.getName());

		return rowView;
	}
}