package com.digilytics.yatin.java.assignment.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	public static final String defaultFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	public static final String defaultTimeFormat = "HH:MM:ss";
	public static final String defaultDateFormat = "yyyy-MM-dd";
	public static final TimeZone defaultTimeZone = TimeZone.getTimeZone("IST");

	public static String today() {
		DateFormat df = new SimpleDateFormat(defaultFormat);
		df.setTimeZone(defaultTimeZone);
		df.setLenient(false);
		return df.format(new Date());
	}

	public static String getTime(long time) {
		DateFormat df = new SimpleDateFormat(defaultTimeFormat);
		df.setLenient(false);
		return df.format(new Date(time));
	}

	public static String getDate(long time) {
		DateFormat df = new SimpleDateFormat(defaultDateFormat);
		df.setLenient(false);
		return df.format(new Date(time));
	}

	public static String get(Date date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		df.setTimeZone(defaultTimeZone);
		df.setLenient(false);
		return df.format(date);
	}

}
