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
		this.userId = -1;
		this.name = "";
		this.orders = new ArrayList<Order>();
		this.passcode = "-1";
		this.manager = false;
	}

	public User(int id, String n) {
		this.userId = id;
		this.name = n;
		this.orders = new ArrayList<Order>();
		this.passcode = setPasscode(id);
		this.manager = false;
	}
	
	public User(int id, String n, ArrayList<Order> list) {
		this.userId = id;
		this.name = n;
		this.orders.addAll(list);
		this.passcode = setPasscode(id);
		this.manager = false;
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
		return this.userId;
	}

	public void setUserId(int id) {
		this.userId = id;
		this.setPasscode(id);
	}

	@DynamoDBAttribute(attributeName = "Name")
	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}
	
	@DynamoDBAttribute(attributeName = "Passcode")
	public String getPasscode() {
		return this.passcode;
	}
	public void setPasscode(String p) {
		this.passcode = p;
	}
	
	@DynamoDBTypeConverted(converter = MyBooleanConverter.class)
	public boolean isManager() {
		return this.manager;
	}

	public void setManager(boolean m) {
		this.manager = m;
	}

	@DynamoDBTypeConverted(converter = MyOrderConverter.class)
	public ArrayList<Order> getOrderList() {
		if(this.orders == null) {
			orders = new ArrayList<Order>();
		}
		return this.orders;
	}
	
	public void setOrderList(ArrayList<Order> list) {
		this.orders.addAll(list);
	}
	
	public boolean removeOrder(int num) {
		boolean flag = false;
		// do something
		return flag;
	}
}
