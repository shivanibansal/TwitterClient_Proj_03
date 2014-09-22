package com.yahoo.bshivani.basictwitter.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable{
	private static final long serialVersionUID = 1L;
	private String 	body;
	private long	uid;	// tweet id
	private User 	user;
	private String	createdAt;
	
	public static Tweet fromJson(JSONObject obj) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = obj.getString("text");
			tweet.uid = obj.getLong("id");
			tweet.createdAt = obj.getString("created_at");
			tweet.user = User.fromJSON(obj.getJSONObject("user"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray arr) {
		ArrayList<Tweet> tweetArr = new ArrayList<Tweet>(arr.length());
		int cnt = arr.length();
		for (int i=0; i<cnt; i++)
		{
			try {
				Tweet tweet = Tweet.fromJson(arr.getJSONObject(i));
				tweetArr.add(tweet);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tweetArr;
	}
	
	public static long lowestTweetIdfromJsonArray(JSONArray arr) {
		long uid = 0;
		try {
//			Tweet tweet = (Tweet) arr.get(arr.length() -1);
//			uid = tweet.uid;
			JSONObject tweetJsonObj = arr.getJSONObject(arr.length() -1);
			uid = tweetJsonObj.getLong("id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return uid;
	}

	public String getBody() {
		return body;
	}

	public long getUid() {
		return uid;
	}

	public User getUser() {
		return user;
	}

	public String getCreatedAt() {
		return createdAt;
	}
	
}
