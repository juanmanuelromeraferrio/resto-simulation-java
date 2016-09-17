package com.fiuba.model;

import com.fiuba.utils.Logger;
import com.fiuba.utils.Utils;

public class Diner implements Runnable {

	public String name;
	private Restaurant restaurant;
	private Table table;
	private Order order;
	private int timeToEat;

	public Diner(String name, Restaurant restaurant, Order order,
			int timeInRestaurant) {
		this.name = name;
		this.restaurant = restaurant;
		this.order = order;
		this.timeToEat = timeInRestaurant;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		synchronized (this) {
			Logger.log(this + " seating in table " + table);
			this.table = table;
			this.table.setDiner(this);
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
		order();
		eating();
		pay();
		restaurant.leave(this);
	}

	private void order() {
		synchronized (this) {
			try {
				table.setState(TableState.WAITING_TO_ORDER);
				this.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	private void eating() {
		Logger.log(this + " eating " + timeToEat + " secs");
		table.setState(TableState.EATING);
		Utils.sleep(timeToEat);
	}

	private void pay() {
		synchronized (this) {
			try {
				table.setState(TableState.WAITING_TO_PAY);
				this.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public Order getOrder() {
		return order;
	}
}
