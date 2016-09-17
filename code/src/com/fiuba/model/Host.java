package com.fiuba.model;

import com.fiuba.utils.Configuration;
import com.fiuba.utils.Logger;
import com.fiuba.utils.Utils;

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

			if (tableIsEmpty(table)) {
				boolean attendLiving = attendLivingDinerIfNecessary(table);
				if (!attendLiving) {
					boolean attendDoor = attendDoorDinerIfNecessary(table);
					if (!attendDoor) {
						restaurant.putFreeTable(table);
					}
				}
			} else {
				putDinerInLivingIfNecessary();
			}

			Utils.sleep(Configuration.HOST_TASK_TIME);
		}
	}

	private boolean tableIsEmpty(Table table) {
		return table != null;
	}

	private boolean attendLivingDinerIfNecessary(Table table) {
		Diner dinnerInLiving = restaurant.getDinerInLiving();
		if (dinnerInLiving != null) {
			Logger.log(this + " attend to " + dinnerInLiving);
			dinnerInLiving.setTable(table);
			return true;
		}
		return false;
	}

	private boolean attendDoorDinerIfNecessary(Table table) {
		Diner dinnerInDoor = restaurant.getDinerInDoor();
		if (dinnerInDoor != null) {
			Logger.log(this + " attend to " + dinnerInDoor);
			dinnerInDoor.setTable(table);
			return true;
		}
		return false;
	}

	private void putDinerInLivingIfNecessary() {
		Diner dinnerInDoor = restaurant.getDinerInDoor();
		if (dinnerInDoor != null) {
			Logger.log(this + " attend to " + dinnerInDoor);
			restaurant.takeToLiving(dinnerInDoor);
		}
	}
}
