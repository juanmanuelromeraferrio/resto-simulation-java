package com.fiuba.model;

import java.util.concurrent.TimeUnit;

import com.fiuba.utils.Logger;

public class Diner implements Runnable {

	public String name;
	private Restaurant restaurant;
	private Table table;
	private int timeInRestaurant;

	public Diner(String name, Restaurant restaurant, int timeInRestaurant) {
		this.name = name;
		this.restaurant = restaurant;
		this.timeInRestaurant = timeInRestaurant;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		synchronized (this) {
			Logger.log(this + " seating in table " + table);
			this.table = table;
			notify();
		}
	}

	@Override
	public String toString() {
		if (table != null) {
			return "Diner [name=" + name + ", table=" + table + "]";
		} else {
			return "Diner [name=" + name + "]";
		}
	}

	@Override
	public void run() {
		restaurant.enter(this);
		eating();// waiter.order();
		restaurant.leave(this);
	}

	private void eating() {
		try {
			Logger.log(this + " eating " + timeInRestaurant + " secs");
			TimeUnit.SECONDS.sleep(timeInRestaurant);
		} catch (InterruptedException e) {
		}
	}
}
