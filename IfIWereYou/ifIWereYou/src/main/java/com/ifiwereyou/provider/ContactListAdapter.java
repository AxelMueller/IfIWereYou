package com.ifiwereyou.provider;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.utils.ContactComperator;
import com.parse.ParseUser;

import java.util.Collections;
import java.util.List;

public class ContactListAdapter extends ArrayAdapter<ParseUser> {

	private final Activity context;
	private List<ParseUser> contacts;

    //TODO write class inheriting from ParseUser ?
    public static final int FIRSTNAME = 0;
    public static final int LASTNAME = 1;
    public static final int SCORE = 2;

	static class ViewHolder {
		public TextView contactNameTextView;
	}

	public ContactListAdapter(Activity context, List<ParseUser> contacts) {
		super(context, R.layout.row_contact_list, contacts);
		this.context = context;
		this.contacts = contacts;
		Collections.sort(this.contacts,
				new ContactComperator(ContactListAdapter.FIRSTNAME));
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
        String firstname = (String) contacts.get(position).get("firstname");
        String lastname = (String)contacts.get(position).get("lastname");
        String fullName = firstname+" "+lastname;
		holder.contactNameTextView.setText(fullName);

		return rowView;
	}
}