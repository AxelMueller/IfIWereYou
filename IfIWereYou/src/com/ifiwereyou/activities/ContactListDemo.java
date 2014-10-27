package com.ifiwereyou.activities;

import java.util.ArrayList;
import java.util.Collections;

import com.ifiwereyou.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContactListDemo extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
	    getActionBar().hide();
		setContentView(R.layout.contacts);
		ListView list = (ListView) findViewById(R.id.listView1);
		ArrayList<String> contactsList = new ArrayList<String>();
		contactsList.add("Axel Müller");
		contactsList.add("Hendrik Böwer");
		contactsList.add("Simon Tenbeitel");
		contactsList.add("Max Mustermann");
		contactsList.add("Andrea Musterfrau");
		Collections.sort(contactsList);
		list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList));
		super.onCreate(savedInstanceState);
	}
	
}
