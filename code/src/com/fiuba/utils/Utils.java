package com.fiuba.utils;

import java.util.concurrent.TimeUnit;

public class Utils {

	public static void sleep(long time) {
		try {

			TimeUnit.SECONDS.sleep(time);
		} catch (InterruptedException e) {
		}
	}
}
