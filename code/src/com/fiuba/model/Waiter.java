package com.fiuba.model;

import com.fiuba.utils.Configuration;
import com.fiuba.utils.Logger;
import com.fiuba.utils.Utils;

public class Waiter implements Runnable {

	private Restaurant restaurant;
	private String name;

	public Waiter(String name, Restaurant restaurant) {
		super();
		this.name = name;
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		while (true) {
			takeOrder();
			dispatchOrder();
			receivePay();
		}
	}

	@Override
	public String toString() {
		return "Waiter [name=" + name + "]";
	}

	private void takeOrder() {
		Table table = restaurant.getTableWaitingToOrder();
		if (table != null) {

			Logger.log(this + " taking order to " + table);

			table.setState(TableState.WAITING_FOR_ORDER);
			Order order = table.getDiner().getOrder();
			order.setTable(table);
			restaurant.putOrderToCook(order);
			Utils.sleep(Configuration.WAITER_TAKE_ORDER_TIME);
		}

	}

	private void dispatchOrder() {
		Order order = restaurant.getAvailableOrder();
		if (order != null) {
			Table table = order.getTable();
			Diner diner = table.getDiner();

			Logger.log(this + " dispatching order to " + diner);

			synchronized (diner) {
				table.setState(TableState.EATING);
				diner.notify();
			}
			restaurant.putBusyTable(table);
			Utils.sleep(Configuration.WAITER_DISPATCH_ORDER_TIME);
		}

	}

	private void receivePay() {
		Table table = restaurant.getTableWaitingToPay();
		if (table != null) {
			Diner diner = table.getDiner();

			Logger.log(this + " receiving pay for " + diner);

			table.setState(TableState.WAITING_TO_LEAVE);
			restaurant.putBusyTable(table);
			synchronized (diner) {
				diner.notify();
			}
			Utils.sleep(Configuration.WAITER_RECEIVE_PAY);
		}

	}
}
