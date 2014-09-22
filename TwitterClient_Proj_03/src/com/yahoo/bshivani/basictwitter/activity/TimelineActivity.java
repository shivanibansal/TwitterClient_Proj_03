package com.yahoo.bshivani.basictwitter.activity;

import java.util.ArrayList;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.bshivani.basictwitter.ComposeActivity;
import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.TwitterClient;
import com.yahoo.bshivani.basictwitter.adapters.TweetAdapter;
import com.yahoo.bshivani.basictwitter.listener.EndlessScrollListener;
import com.yahoo.bshivani.basictwitter.models.Tweet;
import com.yahoo.bshivani.basictwitter.utils.UtilsClass;

public class TimelineActivity extends Activity {
	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private TweetAdapter tweetAdapter;
	// private ArrayAdapter<Tweet> aTweets;
	private ListView lvTweets;
	private final int REQ_COMPOSE_TWEET = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();

		lvTweets = (ListView) findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet>();
		tweetAdapter = new TweetAdapter(this, tweets);
		// aTweets = new ArrayAdapter<Tweet>(this,
		// android.R.layout.simple_list_item_1, tweets);
		lvTweets.setAdapter(tweetAdapter);

		UtilsClass.setApplicationContext(getBaseContext());
		setUpScrollListenerForListView();
		setActionBar();
		populateTimeline();
	}

	public void setUpScrollListenerForListView() {
		lvTweets.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				System.out.println("onLoadMore");
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				// or customLoadMoreDataFromApi(totalItemsCount);
				customLoadMoreDataFromApi(page);
			}
		});
	}

	private void customLoadMoreDataFromApi(int offset) {
		// TODO Auto-generated method stub

		populateTimeline();
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void populateTimeline() {
		client.getHomeTimeline(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", json.toString());
				System.out.println("# of Tweets : " + json.length());
				tweetAdapter.addAll(Tweet.fromJsonArray(json));
				tweetAdapter.notifyDataSetChanged();
				client.setMaxId(Tweet.lowestTweetIdfromJsonArray(json));
			};

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("debug", e.toString());
				Log.d("debug", s.toString());
			}
		});
	}

	public void setActionBar() {
		client.getAccountDetails(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray json) {
				Log.d("setActionBar : debug", json.toString());
				// tweetAdapter.addAll(Tweet.fromJsonArray(json));
				// tweetAdapter.notifyDataSetChanged();
			};

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("setActionBar : debug", e.toString());
				Log.d("setActionBar : debug", s.toString());
			}
		});
	}

	public void onClickComposeTweet(MenuItem mi) {
		Intent i = new Intent(this, ComposeActivity.class);
		startActivityForResult(i, REQ_COMPOSE_TWEET);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQ_COMPOSE_TWEET) {
			if (resultCode == RESULT_OK) {
				/*
				 * Story : User should be taken back to home timeline with new
				 * tweet visible
				 */
				Tweet newerTweet = (Tweet) data.getSerializableExtra("NewerTweet");
				tweetAdapter.insert(newerTweet, 0);
				tweetAdapter.notifyDataSetChanged();
			}
		}
	}
}
