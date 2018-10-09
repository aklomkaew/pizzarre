package com.amazonaws;

import java.util.*;

public class Cart {
	private ArrayList<Pizza> pizzas;

	private int orderNumber;
	private boolean active;

	public Cart() {
		pizzas = new ArrayList<Pizza>();
		orderNumber = -1;
		active = true;
	}

	public Cart(int num, ArrayList<Pizza> list) {
		orderNumber = num;
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
}
