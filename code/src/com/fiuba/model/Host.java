package com.fiuba.model;

import java.util.concurrent.TimeUnit;

import com.fiuba.utils.Logger;

public class Host implements Runnable {

	private Restaurant restaurant;
	private String name;

	public Host(String name, Restaurant restaurant) {
		super();
		this.name = name;
		this.restaurant = restaurant;
	}

	@Override
	public String toString() {
		return "Host [name=" + name + "]";
	}

	@Override
	public void run() {
		while (true) {
			Table table = restaurant.getFreeTable();

			// Si hay mesa vacia
			if (table != null) {
				// Si hay gente en el living
				Diner dinnerInLiving = restaurant.getDinerInLiving();
				if (dinnerInLiving != null) {
					Logger.log(this + " attend to " + dinnerInLiving);
					dinnerInLiving.setTable(table);
				} else {
					// Si hay gente en la puerta
					Diner dinnerInDoor = restaurant.getDinerInDoor();
					if (dinnerInDoor != null) {
						Logger.log(this + " attend to " + dinnerInDoor);
						dinnerInDoor.setTable(table);
					} else {
						restaurant.putFreeTable(table);
					}
				}
			} else {
				Diner dinnerInDoor = restaurant.getDinerInDoor();
				if (dinnerInDoor != null) {
					Logger.log(this + " attend to " + dinnerInDoor);
					restaurant.takeToLiving(dinnerInDoor);
				}
			}

			sleep();
		}
	}

	private void sleep() {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		}
	}

}
