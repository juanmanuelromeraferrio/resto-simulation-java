package com.fiuba.main;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.fiuba.model.Diner;
import com.fiuba.model.Host;
import com.fiuba.model.Restaurant;

public class RestaurantMain {

	public static void main(String[] args) throws InterruptedException {
		int tables = 1;
		int diners = 3;
		int host = 2;
		Restaurant restaurant = new Restaurant(tables);

		for (int i = 0; i < diners; i++) {
			(new Thread(new Diner("" + i, restaurant, random()))).start();
		}
		for (int i = 0; i < host; i++) {
			(new Thread(new Host("" + i, restaurant))).start();
		}

		TimeUnit.SECONDS.sleep(5);

		for (int i = diners; i < 2 * diners; i++) {
			(new Thread(new Diner("" + i, restaurant, random()))).start();
		}

	}

	public static int random() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10) + 10;
		return randomInt;
	}

}
