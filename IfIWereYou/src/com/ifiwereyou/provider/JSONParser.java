package com.ifiwereyou.provider;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	// constructor
	public JSONParser() {

	}

	public JSONObject getJSONFromUrl(String url, List<NameValuePair> params) {

		URLTask task = new URLTask(params);
		try {
			return task.execute(url).get(); // Task is not async anymore but in
											// sync
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
