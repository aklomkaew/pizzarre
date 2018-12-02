package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents a manager
 * @author Atchima
 *
 */
@DynamoDBTable(tableName = "my-users-table")
public class Manager extends User {

	/**
	 * Class constructor, calls its base class User
	 */
	public Manager() {
		super();
		manager = true;
	}

	/**
	 * Creates a manager with specified id and name
	 * @param id An integer representing the manager's ID
	 * @param n A string representing the manager's name
	 */
	public Manager(int id, String n) {
		super(id, n);
		manager = true;
	}
}
