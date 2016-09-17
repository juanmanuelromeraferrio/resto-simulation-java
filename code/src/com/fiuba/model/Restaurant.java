package com.fiuba.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fiuba.utils.Logger;

public class Restaurant {

	public Queue<Table> tables;
	public Queue<Table> busyTables;

	public Queue<Diner> dinnerInDoor;
	public Queue<Diner> dinnerInLiving;

	public Queue<Order> orderToCoke;
	public Queue<Order> availableOrder;

	public Restaurant(int tables) {
		this.tables = new ConcurrentLinkedQueue<Table>();
		this.busyTables = new ConcurrentLinkedQueue<Table>();
		this.dinnerInDoor = new ConcurrentLinkedQueue<Diner>();
		this.dinnerInLiving = new ConcurrentLinkedQueue<Diner>();
		this.orderToCoke = new ConcurrentLinkedQueue<Order>();
		this.availableOrder = new ConcurrentLinkedQueue<Order>();

		for (int i = 0; i < tables; i++) {
			this.tables.add(new Table(i));
		}
	}

	public void enter(Diner diner) {
		synchronized (diner) {
			try {
				Logger.log(diner + " enter to Restaurant");
				dinnerInDoor.add(diner);
				diner.wait();
			} catch (InterruptedException e) {
			}
		}
	}

	public Diner getDinerInDoor() {
		synchronized (dinnerInDoor) {
			return dinnerInDoor.poll();
		}
	}

	public void leave(Diner diner) {
		synchronized (busyTables) {
			Logger.log(diner + " leaving for Restaurant");
			Table table = diner.getTable();
			table.setState(TableState.FREE);
			busyTables.remove(table);
			tables.add(table);
		}
	}

	public Table getFreeTable() {
		synchronized (tables) {
			Table table = tables.poll();
			if (table != null) {
				busyTables.add(table);
			}
			return table;
		}
	}

	public void takeToLiving(Diner diner) {
		synchronized (dinnerInLiving) {
			Logger.log(diner + " enter to Living");
			dinnerInLiving.add(diner);
		}
	}

	public Diner getDinerInLiving() {
		synchronized (dinnerInLiving) {
			return dinnerInLiving.poll();
		}
	}

	public void putFreeTable(Table table) {
		synchronized (tables) {
			tables.add(table);
		}
	}

	public void putBusyTable(Table table) {
		synchronized (busyTables) {
			busyTables.add(table);
		}
	}

	public Table getTableWaitingToOrder() {
		synchronized (busyTables) {
			for (Table table : busyTables) {
				if (table.isWaitingToOrder()) {
					busyTables.remove(table);
					return table;
				}
			}
		}
		return null;
	}

	public Table getTableWaitingToPay() {
		synchronized (busyTables) {
			for (Table table : busyTables) {
				if (table.isWaitingToPay()) {
					busyTables.remove(table);
					return table;
				}
			}
		}
		return null;
	}

	public Order getOrderToCook() {
		synchronized (orderToCoke) {
			return orderToCoke.poll();
		}
	}

	public void putOrderToCook(Order order) {
		synchronized (orderToCoke) {
			orderToCoke.add(order);
		}
	}

	public Order getAvailableOrder() {
		synchronized (availableOrder) {
			return availableOrder.poll();
		}
	}

	public void putOrderToAvailable(Order order) {
		synchronized (availableOrder) {
			availableOrder.add(order);
		}
	}

}
