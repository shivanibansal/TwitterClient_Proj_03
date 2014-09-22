package com.yahoo.bshivani.basictwitter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.format.DateUtils;

public class UtilsClass {
	public static Context appContext;

	public static final String getRelativeTime(String dateStr)
	{
		Date dateObj;
		String strRelativeDate = null; 
		{
			String dtStart = dateStr;
			SimpleDateFormat  format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
				try {
					dateObj = format.parse(dtStart);
					 System.out.println(dateObj);
					 strRelativeDate = (String) DateUtils.getRelativeDateTimeString(
							 					appContext, 					// Suppose you are in an activity or other Context subclass
							 					dateObj.getTime(), 			// The time to display
							 					DateUtils.MINUTE_IN_MILLIS, // The resolution. This will display only 
							 												// minutes (no "3 seconds ago") 
							 					DateUtils.WEEK_IN_MILLIS, 	// The maximum resolution at which the time will switch 
							 												// to default date instead of spans. This will not 
																			// display "3 weeks ago" but a full date instead
							 					(DateUtils.FORMAT_ABBREV_RELATIVE | DateUtils.FORMAT_NUMERIC_DATE)); // Eventual flags
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
		}
		return strRelativeDate;
	}
	
	public final static void setApplicationContext(Context context) {
		appContext = context;
	}
}
