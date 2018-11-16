package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBConvertedBool;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName = "my-carts-table")
public class Cart {
	private ArrayList<Pizza> pizzas;
	// private ArrayList<Drink> drinks;

	private int orderNumber;
	private boolean active;
	private double total;
	private String server;
	private int serverId;

	public Cart() {
		this.pizzas = new ArrayList<Pizza>();
		this.orderNumber = -1;
		this.active = true;
		this.total = 0.0;
	}

	public Cart(int num, List<Pizza> list) {
		this.orderNumber = num;
		this.pizzas = new ArrayList<Pizza>();
		this.pizzas.addAll(list);
		this.active = true;
		this.total = 0.0;
	}

	@DynamoDBHashKey(attributeName = "OrderNumber")
	public int getOrderNumber() {
		return this.orderNumber;
	}

	public void setOrderNumber(int num) {
		this.orderNumber = num;
	}

	@DynamoDBTypeConverted(converter = MyBooleanConverter.class)
	public boolean getState() {
		return this.active;
	}

	public void setState(boolean s) {
		this.active = s;
	}
	
	public void setInactive() {
		this.active = false;
	}

	@DynamoDBAttribute(attributeName = "Server")
	public String getServerName() {
		return this.server;
	}

	public void setServerName(String u) {
		this.server = u;
	}
	
	@DynamoDBAttribute(attributeName = "ServerId")
	public int getServerId() {
		return this.serverId;
	}

	public void setServerId(int id) {
		this.serverId = id;
	}

	@DynamoDBAttribute(attributeName = "Total")
	public double getTotal() {
		return this.total;
	}

	public void setTotal(double t) {
		this.total = t;
	}

	@DynamoDBTypeConverted(converter = MyPizzaConverter.class)
	public ArrayList<Pizza> getPizzas() {
		return this.pizzas;
	}
	public void setPizzas(ArrayList<Pizza> list) {
		pizzas = list;
	}

	public void addPizza(Pizza p) {
		this.pizzas.add(p);
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
