package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-users")
public class User {
	private int userId;
	private String name;

	public User() {
		userId = -1;
		name = "";
	}

	public User(int id, String n) {
		userId = id;
		name = n;
	}

	@DynamoDBHashKey(attributeName = "Id")
	public int getUserId() {
		return userId;
	}

	public void setUserId(int id) {
		userId = id;
	}

	@DynamoDBAttribute(attributeName="Name")
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}
}
