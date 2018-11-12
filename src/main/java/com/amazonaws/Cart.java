package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-carts-table")
public class Cart {
	private ArrayList<Pizza> pizzas;
	// private ArrayList<Drink> drinks;

	private int orderNumber;
	private boolean active;
	private double total;
	private String server;

	public Cart() {
		pizzas = new ArrayList<Pizza>();
		orderNumber = -1;
		active = true;
		total = 0.0;
	}

	public Cart(int num, ArrayList<Pizza> list) {
		orderNumber = num;
		pizzas = new ArrayList<Pizza>();
		pizzas.addAll(list);
		active = true;
		total = 0.0;
	}

	@DynamoDBHashKey(attributeName = "Order Number")
	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int num) {
		orderNumber = num;
	}

	@DynamoDBAttribute(attributeName = "Pizzas")
	public ArrayList<Pizza> getPizzas() {
		return pizzas;
	}
	public void addPizza(Pizza p) {
		pizzas.add(p);
	}

	@DynamoDBAttribute(attributeName = "Status")
	public boolean getState() {
		return active;
	}

	public void setInactive() {
		active = false;
	}

	@DynamoDBAttribute(attributeName = "Server")
	public String getServerName() {
		return server;
	}

	public void setServerName(String u) {
		server = u;
	}

	@DynamoDBAttribute(attributeName = "Total")
	public double getTotal() {
		return total;
	}

	public void setTotal(double t) {
		total = t;
	}

	public String toString() {
		String ret = "Order #" + orderNumber + ": " + pizzas.size() + " pizza. Order is "
				+ ((active) ? "active" : "not active") + ".";

		for (int i = 0; i < pizzas.size(); i++) {
			ret += "\n-->Pizza #" + (i + 1) + " | " + pizzas.get(i).toString();
		}

		return ret;
	}
}
