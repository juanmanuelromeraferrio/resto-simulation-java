package com.fiuba.model;

public class Order {

	private Table table;
	private long timeToCook;
	private int price;

	public Order(long timeToCook, int price) {
		super();
		this.timeToCook = timeToCook;
		this.price = price;
	}

	@Override
	public String toString() {
		return "Order [table=" + table + ", timeToCook=" + timeToCook + "]";
	}

	public long getTimeToCook() {
		return timeToCook;
	}

	public int getPrice() {
		return price;
	}

	public Table getTable() {
		return this.table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

}
