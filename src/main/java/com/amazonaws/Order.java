package com.amazonaws;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBConvertedBool;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMarshalling;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@DynamoDBTable(tableName = "my-orders-table")
public class Order {
	@JsonIgnore
	private ArrayList<Pizza> pizzas;
	private ArrayList<Drink> drinks;
	// private ArrayList<Integer> drinkQuantity;
	// private HashMap<Drink, List<Drink>> drinkMap;

	private int orderNumber;
	private boolean active;
	private double total;
	private String server;
	private int serverId;
	private int discount;

	public Order() {
		this.pizzas = new ArrayList<Pizza>();
		this.drinks = new ArrayList<Drink>();
		// this.drinkQuantity = new ArrayList<Integer>();
		// this.drinkMap = new HashMap<Drink, List<Drink>>();
		this.orderNumber = -1;
		this.active = true;
		this.total = 0.0;
		this.discount = 0;
	}

	public Order(int num, List<Pizza> list) {
		this.orderNumber = num;
		this.pizzas = new ArrayList<Pizza>();
		this.drinks = new ArrayList<Drink>();
		// this.drinkQuantity = new ArrayList<Integer>();
		// this.drinkMap = new HashMap<Drink, List<Drink>>();
		this.pizzas.addAll(list);
		this.active = true;
		this.total = 0.0;
		this.discount = 0;
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
		this.total = round(total, 2);
		return this.total;
	}

	public void setTotal(double t) {
		this.total = t;
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	@DynamoDBAttribute(attributeName = "Discount")
	public int getDiscount() {
		return this.discount;
	}

	public void setDiscount(int d) {
		this.discount = d;
	}

	@DynamoDBTypeConverted(converter = MyPizzaConverter.class)
	public ArrayList<Pizza> getPizzas() {
		return this.pizzas;
	}

	public void setPizzas(ArrayList<Pizza> list) {
		this.pizzas.addAll(list);
	}

	public void addPizza(Pizza p) {
		this.pizzas.add(p);
	}

	@DynamoDBTypeConverted(converter = MyDrinkConverter.class)
	public ArrayList<Drink> getDrink() {
		if (drinks == null) {
			System.out.println("drink is null");
			drinks = new ArrayList<Drink>();
		}
		return this.drinks;
	}

	public void setDrink(ArrayList<Drink> list) {
		this.drinks.addAll(list);
	}

//	@JsonProperty("drinkList")
//	@DynamoDBAttribute(attributeName = "DrinkMap")
//    private List<KeyValueContainer<Drink, List<Drink>>> getDrinkList() {
//        return ObjectUtils.toList(drinkMap);
//    }
//
//    @JsonProperty("drinkList")
//    private void setTeamList(List<KeyValueContainer<Drink, List<Drink>>> list) {
//        drinkMap = (HashMap<Drink, List<Drink>>) ObjectUtils.toMap(list);
//    }
//	
//	@DynamoDBTypeConverted(converter = MyDrinkMapConverter.class)
//	public HashMap<Drink, List<Drink>> getDrinkQuantity() {
//		return this.drinkMap;
//	}
//	public void setDrinkQuantity(HashMap<Drink, List<Drink>> map) {
//		drinkMap = map;
//	}

	public void applyDiscount() {
		this.total = this.total * ((100.0 - this.discount) / 100.0);
	}

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
