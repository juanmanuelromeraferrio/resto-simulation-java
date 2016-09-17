package com.fiuba.model;

import com.fiuba.utils.Logger;
import com.fiuba.utils.Utils;

public class Cook implements Runnable {

	private Restaurant restaurant;

	public Cook(Restaurant restaurant) {
		super();
		this.restaurant = restaurant;
	}

	@Override
	public void run() {
		while (true) {
			cookOrder();
		}
	}

	@Override
	public String toString() {
		return "Cook []";
	}

	private void cookOrder() {
		Order order = restaurant.getOrderToCook();
		if (order != null) {
			Logger.log(this + " cooking " + order);
			Utils.sleep(order.getTimeToCook());
			restaurant.putOrderToAvailable(order);
		}
	}
}
