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
	protected ArrayList<Order> orders;
	protected String passcode;
	protected boolean manager;

	public User() {
		userId = -1;
		name = "";
		orders = new ArrayList<Order>();
		passcode = "-1";
		manager = false;
	}

	public User(int id, String n) {
		userId = id;
		name = n;
		orders = new ArrayList<Order>();
		passcode = setPasscode(id);
		manager = false;
	}
	
	public User(int id, String n, ArrayList<Order> list) {
		userId = id;
		name = n;
		orders.addAll(list);
		passcode = setPasscode(id);
		manager = false;
	}

	private String setPasscode(int id) {
		String ret = "";
		for(int i = 0; i < 4; i++) {
			ret += Integer.toString(id);
		}
		return ret;
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
	
	@DynamoDBAttribute(attributeName = "Passcode")
	public String getPasscode() {
		return passcode;
	}
	public void setPasscode(String p) {
		passcode = p;
	}
	
	@DynamoDBTypeConverted(converter = MyBooleanConverter.class)
	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean m) {
		manager = m;
	}

	@DynamoDBTypeConverted(converter = MyOrderConverter.class)
	public ArrayList<Order> getOrderList() {
		return orders;
	}
	
	public void setOrderList(ArrayList<Order> list) {
		orders.addAll(list);
	}
	
	public boolean removeOrder(int num) {
		boolean flag = false;
		// do something
		return flag;
	}
}
