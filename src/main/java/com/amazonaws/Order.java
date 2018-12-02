package com.amazonaws;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

/**
 * Represents an order
 * @author Atchima
 *
 */
@DynamoDBTable(tableName = "my-orders-table")
public class Order {
	private ArrayList<Pizza> pizzas;
	private ArrayList<Drink> drinks;
	private int orderNumber;
	private boolean status;
	private double total;
	private String server;
	private int serverId;
	private int discount;

	/**
	 * Class constructor
	 */
	public Order() {
		this.pizzas = new ArrayList<Pizza>();
		this.drinks = new ArrayList<Drink>();
		this.orderNumber = -1;
		this.status = true;
		this.total = 0.0;
		this.discount = 0;
	}

	/**
	 * Creates an order with the specified orderNumber and list of Pizza
	 * @param num An integer representing the order's orderNumber
	 * @param list A list of Pizza representing the order's pizzas
	 */
	public Order(int num, List<Pizza> list) {
		this.orderNumber = num;
		this.pizzas = new ArrayList<Pizza>();
		this.drinks = new ArrayList<Drink>();
		this.pizzas.addAll(list);
		this.status = true;
		this.total = 0.0;
		this.discount = 0;
	}

	/**
	 * Gets the order's orderNumber
	 * @return An integer representing the order's orderNumber
	 */
	@DynamoDBHashKey(attributeName = "OrderNumber")
	public int getOrderNumber() {
		return this.orderNumber;
	}

	/**
	 * Sets the order's orderNumber
	 * @param num An integer representing the order's number
	 */
	public void setOrderNumber(int num) {
		this.orderNumber = num;
	}

	/**
	 * Gets the order's state
	 * @return True if the order is active, false otherwise
	 */
	@DynamoDBTypeConverted(converter = MyBooleanConverter.class)
	public boolean getState() {
		return this.status;
	}

	/**
	 * Sets the order's state
	 * @param s A boolean representing the order's state
	 */
	public void setState(boolean s) {
		this.status = s;
	}

	/**
	 * Sets the order's status to inactive
	 */
	public void setInactive() {
		this.status = false;
	}

	/**
	 * Gets the order's server name
	 * @return A string representing the order's server name
	 */
	@DynamoDBAttribute(attributeName = "Server")
	public String getServerName() {
		return this.server;
	}

	/**
	 * Sets the order's server name
	 * @param u A string representing the order's server name
	 */
	public void setServerName(String u) {
		this.server = u;
	}

	/**
	 * Gets the order's server ID
	 * @return An integer representing the order's server ID
	 */
	@DynamoDBAttribute(attributeName = "ServerId")
	public int getServerId() {
		return this.serverId;
	}

	/**
	 * Sets the order's server ID
	 * @param id An integer representing the order's server ID
	 */
	public void setServerId(int id) {
		this.serverId = id;
	}

	/**
	 * Gets the order's total cost
	 * @return A double representing the order's total cost
	 */
	@DynamoDBAttribute(attributeName = "Total")
	public double getTotal() {
		this.total = round(total, 2);
		return this.total;
	}

	/**
	 * Sets the order's total cost
	 * @param t A double representing the order's total cost
	 */
	public void setTotal(double t) {
		this.total = t;
	}

	/**
	 * Rounds up the order's total cost to the nearest hundredth decimal
	 * @param value A double representing the order's total
	 * @param places An integer representing the number of decimal places
	 * @return A double representing the rounded order's total cost
	 */
	private double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	/**
	 * Gets the order's discount
	 * @return A double representing the order's discount
	 */
	@DynamoDBAttribute(attributeName = "Discount")
	public int getDiscount() {
		return this.discount;
	}

	/**
	 * Sets the order's discount
	 * @param d A double representing the order's discount
	 */
	public void setDiscount(int d) {
		this.discount = d;
	}

	/**
	 * Gets the order's list of Pizza
	 * @return An ArrayList of Pizza representing the order's pizzas
	 */
	@DynamoDBTypeConverted(converter = MyPizzaConverter.class)
	public ArrayList<Pizza> getPizzas() {
		return this.pizzas;
	}

	/**
	 * Sets the order's list of Pizza
	 * @param list An ArrayList of Pizza representing the order's pizzas
	 */
	public void setPizzas(ArrayList<Pizza> list) {
		this.pizzas.addAll(list);
	}

	/**
	 * Adds a pizza to the order's list of Pizza
	 * @param p A Pizza representing a pizza to be added to the order's list of Pizza
	 */
	public void addPizza(Pizza p) {
		this.pizzas.add(p);
	}

	/**
	 * Gets the order's list of drink
	 * @return An ArrayList of Drink representing the order's list of drink
	 */
	@DynamoDBTypeConverted(converter = MyDrinkConverter.class)
	public ArrayList<Drink> getDrink() {
		if (drinks == null) {
			System.out.println("drink is null");
			drinks = new ArrayList<Drink>();
		}
		return this.drinks;
	}

	/**
	 * Sets the order's list of drink
	 * @param list An ArrayList of Drink representing the order's list of drink
	 */
	public void setDrink(ArrayList<Drink> list) {
		this.drinks.addAll(list);
	}

	/**
	 * Applies the order's discount
	 */
	public void applyDiscount() {
		this.total = this.total * ((100.0 - this.discount) / 100.0);
	}

	/**
	 * Gets the string of all item in the order
	 * @return A string representing information of all item in the order
	 */
	public String toString() {
		String ret = "";

		if (pizzas != null && pizzas.size() > 0) {
			ret += pizzas.size() + ((pizzas.size() == 1) ? " pizza" : " pizzas");
			for (int i = 0; i < pizzas.size(); i++) {
				ret += "\n--> Pizza #" + (i + 1) + " has " + pizzas.get(i).toString();
			}
		}
		ret += "\n";
		if (drinks != null && drinks.size() > 0) {
			ret += drinks.size() + ((drinks.size() == 1) ? " drink" : " drinks");
			for (int i = 0; i < drinks.size(); i++) {
				ret += "\n--> Drink #" + (i + 1) + " has " // + drinkQuantity.get(i) + " "
						+ drinks.get(i).toString();
			}
		}

		return ret;
	}
}
