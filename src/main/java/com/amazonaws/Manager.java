package com.amazonaws;

import java.util.ArrayList;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-users-table")
public class Manager extends User {

	public Manager() {
		super();
		manager = true;
	}

	public Manager(int id, String n) {
		super(id, n);
		manager = true;
	}

	public Manager(int id, String n, ArrayList<Order> list) {
		super(id, n, list);
		manager = true;
	}

	public void restock() throws Exception {
		// inventory restock
		InventoryDb.restock();
	}

	public boolean addUser(int id, User u) {
		return UserDb.addUser(id, u);
	}

}
