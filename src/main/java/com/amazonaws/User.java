package com.amazonaws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-users-table")
public class User {
	protected int userId;
	protected String name;
	protected HashMap<Integer, Cart> orderList;
	protected int passcode;

	public User() {
		userId = -1;
		name = "";
		orderList = new HashMap<Integer, Cart>();
	}

	public User(int id, String n) {
		userId = id;
		name = n;
		orderList = new HashMap<Integer, Cart>();
	}
	
	public User(int id, String n, HashMap<Integer, Cart> list) {
		userId = id;
		name = n;
		orderList = list;
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

	@DynamoDBAttribute(attributeName = "Order List")
	public HashMap<Integer, Cart> getOrderList() {
		return orderList;
	}

	public void addOrder(int n, Cart c) {
		orderList.put(n, c);
	}
	
	public boolean removeOrder(int num) {
		boolean flag = false;
		if(orderList.containsKey(num)) {
			orderList.remove(num);
			flag = true;
		}
		return flag;
	}
}
