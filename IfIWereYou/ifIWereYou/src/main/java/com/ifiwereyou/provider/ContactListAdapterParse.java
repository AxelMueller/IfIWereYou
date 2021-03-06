package com.ifiwereyou.provider;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ifiwereyou.R;
import com.ifiwereyou.objects.Friendship;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by D060426 on 21.04.2015.
 */
public class ContactListAdapterParse extends ParseQueryAdapter<Friendship> {

    private final Activity context;
    //private List<ParseUser> contacts;
//    ParseQueryAdapter.QueryFactory<Friendship> factory;

    public static final int FIRSTNAME = 0;
    public static final int LASTNAME = 1;
    public static final int SCORE = 2;

    public ContactListAdapterParse(Activity context, ParseQueryAdapter.QueryFactory<Friendship> factory) {
        super(context, factory);
        this.context = context;
        // Collections.sort(this.contacts,
        // new ContactComperator(ContactListAdapter.FIRSTNAME));
    }

    @Override
    public View getItemView(Friendship object, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_contact_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseUser currentUser = ParseUser.getCurrentUser();
        ParseUser userA = (ParseUser) object.get("friendA");
        //why does fetchIfNeeded not work?
        if(currentUser != userA) {
            userA.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    ParseUser myUser = (ParseUser) parseObject;
                    String firstname = myUser.getString("firstname");
                    String lastname = myUser.getString("lastname");
                    holder.contactList.setText(firstname + " " + lastname);
                }
            });
        } else {
            ParseUser userB = (ParseUser) object.get("friendB");
            //why does fetchIfNeeded not work?
            userB.fetchIfNeededInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject parseObject, ParseException e) {
                    ParseUser myUser = (ParseUser) parseObject;
                    String firstname = myUser.getString("firstname");
                    String lastname = myUser.getString("lastname");
                    holder.contactList.setText(firstname + " " + lastname);
                }
            });
        }

        return convertView;
    }

    static class ViewHolder {
        //@InjectView(android.R.id.text1) TextView title;
        @InjectView(R.id.row_contact_list_contactNameTextView)
        TextView contactList;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
