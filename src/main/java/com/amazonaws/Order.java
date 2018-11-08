package com.amazonaws;

import java.util.*;

public class Order {
	private ArrayList<Pizza> pizzas;
	//private ArrayList<Drink> drinks;

	private int orderNumber;
	private boolean active;
	private double totalAmount;
	private String user;

	public Order() {
		pizzas = new ArrayList<Pizza>();
		orderNumber = -1;
		active = true;
	}

	public Order(int num, ArrayList<Pizza> list) {
		orderNumber = num;
		pizzas = new ArrayList<Pizza>();
		pizzas.addAll(list);
		active = true;
	}

	public void addPizza(Pizza p) {
		pizzas.add(p);
	}

	public void addAllPizzas(ArrayList<Pizza> list) {
		pizzas.addAll(list);
	}

	public ArrayList<Pizza> getPizzas() {
		return pizzas;
	}

	public void setInactive() {
		active = false;
	}

	public boolean getState() {
		return active;
	}

	public void setOrderNumber(int num) {
		orderNumber = num;
	}

	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setUser(String u) {
		user = u;
	}
	
	public String getUser() {
		return user;
	}
	
	public String toString() {
		String ret = "Order #" + orderNumber + ": " + pizzas.size() + " pizza. Order is " 
				+ ((active) ? "active" : "not active") + ".";
		
		for(int i = 0; i < pizzas.size(); i++) {
			ret += "\n-->Pizza #" + (i+1) + " | "
				+ pizzas.get(i).toString();
		}
		
		return ret;
	}
}
