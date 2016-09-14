package com.fiuba.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Logger {

	public static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss:S");

	public static void log(String log) {
		Date date = Calendar.getInstance().getTime();
		String formatDate = dateFormat.format(date);
		System.out.println(formatDate + " " + log);
	}
}
