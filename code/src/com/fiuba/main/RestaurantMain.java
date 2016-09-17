package com.fiuba.main;

import java.util.Random;

import com.fiuba.model.Cook;
import com.fiuba.model.Diner;
import com.fiuba.model.Host;
import com.fiuba.model.Order;
import com.fiuba.model.Restaurant;
import com.fiuba.model.Waiter;

public class RestaurantMain {

	public static void main(String[] args) throws InterruptedException {
		int tables = 1;
		int diners = 5;
		int host = 2;
		int waiters = 3;
		Restaurant restaurant = new Restaurant(tables);

		for (int i = 0; i < diners; i++) {
			(new Thread(new Diner("" + i, restaurant, createOrder(), random())))
					.start();
		}
		for (int i = 0; i < host; i++) {
			(new Thread(new Host("" + i, restaurant))).start();
		}

		for (int i = 0; i < waiters; i++) {
			(new Thread(new Waiter("" + i, restaurant))).start();
		}

		(new Thread(new Cook(restaurant))).start();
	}

	private static Order createOrder() {
		return new Order(randomTime(), random());
	}

	public static long randomTime() {
		Random randomGenerator = new Random();
		long randomInt = nextLong(randomGenerator, 5) + 5;
		return randomInt;
	}

	static long nextLong(Random rng, long n) {
		// error checking and 2^x checking removed for simplicity.
		long bits, val;
		do {
			bits = (rng.nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public static int random() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(10) + 5;
		return randomInt;
	}

}
