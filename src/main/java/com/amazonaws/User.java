package com.amazonaws;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

@DynamoDBTable(tableName = "my-users-table")
public class User {
	protected int userId;
	protected String name;
	protected ArrayList<Cart> carts;	// arraylist of cart order numbers instead
	protected int passcode;

	public User() {
		userId = -1;
		name = "";
		carts = new ArrayList<Cart>();
	}

	public User(int id, String n) {
		userId = id;
		name = n;
		carts = new ArrayList<Cart>();
	}
	
	public User(int id, String n, ArrayList<Cart> list) {
		userId = id;
		name = n;
		carts = list;
	}

	@DynamoDBHashKey(attributeName = "Id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		userId = id;
	}

	@DynamoDBAttribute(attributeName = "Name")
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

//	@DynamoDBTypeConverted(converter = MyCartConverter.class)
//	public List<Cart> getOrderList() {
//		return carts;
//	}
//
//	public void addOrder(Cart c) {
//		carts.add(c);
//	}
//	
//	@DynamoDBTypeConverted(converter = MyPizzaConverter.class)
//	public List<Pizza> getPizzas() {
//		return this.pizzas;
//	}
//	public void setPizzas(List<Pizza> list) {
//		pizzas = list;
//	}
	
	public boolean removeOrder(int num) {
		boolean flag = false;
		// do something
		return flag;
	}
}
