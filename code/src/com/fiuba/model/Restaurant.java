package com.fiuba.model;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.fiuba.utils.Logger;

public class Restaurant {

	public Queue<Table> tables;
	public Queue<Table> busyTables;

	public Queue<Diner> dinnerInDoor;
	public Queue<Diner> dinnerInLiving;

	public Restaurant(int tables) {
		this.tables = new ConcurrentLinkedQueue<Table>();
		this.busyTables = new ConcurrentLinkedQueue<Table>();
		this.dinnerInDoor = new ConcurrentLinkedQueue<Diner>();
		this.dinnerInLiving = new ConcurrentLinkedQueue<Diner>();

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

}
