package com.amazonaws;

import java.util.HashMap;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;

@DynamoDBTable(tableName = "my-users-table")
public class Manager extends User {
	public Manager() {
		super();
	}
	public Manager(int id, String n) {
		super(id, n);
	}
	public Manager(int id, String n, HashMap<Integer, Cart> list) {
		super(id, n, list);
	}
	
	public void restock() throws Exception {
		// inventory restock
		InventoryDb.restock();
	}
	
	public boolean addUser(int id, User u) {
		return UserDb.addUser(id, u);
	}
}
