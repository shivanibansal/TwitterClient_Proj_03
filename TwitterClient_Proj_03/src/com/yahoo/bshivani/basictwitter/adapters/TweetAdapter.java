package com.yahoo.bshivani.basictwitter.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.yahoo.bshivani.basictwitter.R;
import com.yahoo.bshivani.basictwitter.models.Tweet;
import com.yahoo.bshivani.basictwitter.utils.UtilsClass;

public class TweetAdapter extends ArrayAdapter<Tweet> {

	public TweetAdapter(Context context, ArrayList<Tweet> tweet) {
		super(context, R.layout.tweet_item, tweet);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item, parent, false);
		}
		TextView tvTweetBody = (TextView) convertView.findViewById(R.id.tvBody);
		TextView tvTweetName = (TextView) convertView.findViewById(R.id.tvName);
		TextView tvTweetScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
		ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
		TextView tvTimeStamp = (TextView) convertView.findViewById(R.id.tvTimeStamp);
		
		tvTweetBody.setText(tweet.getBody());
		tvTweetName.setText(tweet.getUser().getName());
		tvTweetScreenName.setText("@" + tweet.getUser().getScreenName());
		
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfieImageUrl(), ivProfileImage);

//		String strTimeStamp = tweet.getCreatedAt();
		String strTimeStamp = UtilsClass.getRelativeTime(tweet.getCreatedAt());
		tvTimeStamp.setText(strTimeStamp);
		
		
		return convertView;
	}
}

//@Override
//public View getView(int position, View convertView, ViewGroup parent) {
//	// Lookup view for data population
//	TextView tvMsg = (TextView) convertView.findViewById(R.id.tvUserMsg);
//	// Populate the data into the template view using the data object
//	tvMsg.setText(uMsg.strMessage);
//	// Return the completed view to render on screen
//	return convertView;
//}