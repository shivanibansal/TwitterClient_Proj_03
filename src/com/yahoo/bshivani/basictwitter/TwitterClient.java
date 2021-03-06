package com.yahoo.bshivani.basictwitter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; 
	public static final String 	REST_URL = "https://api.twitter.com/1.1/";
	public static final String 	REST_CONSUMER_KEY = "DaR6QQTWfe5SaXZ3UZK6t6uKN";
	public static final String 	REST_CONSUMER_SECRET = "nKfhFvqbj7gYsePZl0NdnlIukRYltwinTmmxXpPBE1vAFnACV3";
	public static final String 	REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change this (here and in manifest)
	
	public static final String 	TWITTER_HOME_TIMELINE_URL= "/statuses/home_timeline.json";
	public static final	int 	MAX_NUM_OF_TWEETS = 25;
	public static final String 	TWITTER_HOME_TIMELINE_COUNT_PARAM = "?count=";
	public static final String 	TWITTER_HOME_TIMELINE_MAX_ID_PARAM = "max_id=";
	public static long	max_id	= 0;	 	
	public static final String 	TWITTER_POST_TWEET_URL= "/statuses/update.json?status=";
	public static final String 	TWITTER_USER_ACCOUNT = "/account/verify_credentials.json";
	
	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}
	
	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
//		String apiURL = getApiUrl(TWITTER_HOME_TIMELINE_URL);
		String apiURL ;
		if (max_id == 0) { 
			apiURL = getApiUrl(TWITTER_HOME_TIMELINE_URL + TWITTER_HOME_TIMELINE_COUNT_PARAM + MAX_NUM_OF_TWEETS);
		} else {
			apiURL = getApiUrl(TWITTER_HOME_TIMELINE_URL + TWITTER_HOME_TIMELINE_COUNT_PARAM + MAX_NUM_OF_TWEETS + "&" + TWITTER_HOME_TIMELINE_MAX_ID_PARAM + (max_id-1L));
		}
		RequestParams params = new RequestParams();
		params.put("since_id", "1");
		client.get(apiURL, null, handler);
	}
	
	public void getAccountDetails(AsyncHttpResponseHandler handler) {
		String apiURL = getApiUrl(TWITTER_USER_ACCOUNT);
		client.get(apiURL, null, handler);
	}
	
	public void postTweet(AsyncHttpResponseHandler handler, String strTweet) {
		String apiURL = getApiUrl(TWITTER_POST_TWEET_URL + strTweet);
//		RequestParams params = new RequestParams();
		client.post(apiURL, null, handler);
	}
	

	public long getMaxId() {
		return max_id;
	}
	
	public void setMaxId(long lowestTweetId) {
		max_id = lowestTweetId;
	}
	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}*/

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}