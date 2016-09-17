package com.fiuba.model;

public class Table {

	private int number;
	private TableState state;
	private Diner diner;

	public Table(int i) {
		this.number = i;
		this.state = TableState.FREE;
		this.diner = null;
	}

	public void setState(TableState state) {
		this.state = state;
	}

	public void setDiner(Diner diner) {
		this.diner = diner;
	}

	public Diner getDiner() {
		return this.diner;
	}

	@Override
	public String toString() {
		return "Table [number=" + number + ", state=" + state + "]";
	}

	public boolean isWaitingToOrder() {
		return this.state.equals(TableState.WAITING_TO_ORDER);
	}

	public boolean isWaitingToPay() {
		return this.state.equals(TableState.WAITING_TO_PAY);
	}
}
