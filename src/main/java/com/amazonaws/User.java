package com.amazonaws;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;

/**
 * Represents a user
 * @author Atchima
 *
 */
@DynamoDBTable(tableName = "my-users-table")
public class User {
	protected int userId;
	protected String name;
	protected String passcode;
	protected boolean manager;

	/**
	 * Class constructor
	 */
	public User() {
		this.userId = -1;
		this.name = "";
		this.passcode = "-1";
		this.manager = false;
	}

	/**
	 * Creates a user with the specified ID and name
	 * @param id An integer representing the user's ID
	 * @param n A string representing the user's name
	 */
	public User(int id, String n) {
		this.userId = id;
		this.name = n;
		this.setPasscode();
		this.manager = false;
	}
	
	/**
	 * Sets the passcode for the user to a four-digit number of the user's ID represented in string
	 */
	private void setPasscode() {
		String ret = "";
		for(int i = 0; i < 4; i++) {
			ret += Integer.toString(userId);
		}
		this.passcode = ret;
	}
	
	/**
	 * Gets the user's ID
	 * @return An integer representing the user's ID
	 */
	@DynamoDBHashKey(attributeName = "Id")
	public int getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user's ID
	 * @param id An integer representing the user's ID
	 */
	public void setUserId(int id) {
		this.userId = id;
		this.setPasscode();
	}

	/**
	 * Gets the user's name
	 * @return A string representing the user's name
	 */
	@DynamoDBAttribute(attributeName = "Name")
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the user's name
	 * @param n A string representing the user's name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Gets the user's passcode
	 * @return A string representing the user's passcode
	 */
	@DynamoDBAttribute(attributeName = "Passcode")
	public String getPasscode() {
		return this.passcode;
	}
	
	/**
	 * Sets the user's passcode
	 * @param p A string representing the user's passcode
	 */
	public void setPasscode(String p) {
		this.passcode = p;
	}
	
	/**
	 * Gets the user's role
	 * @return True if the user is a manager, false otherwise
	 */
	@DynamoDBTypeConverted(converter = MyBooleanConverter.class)
	public boolean isManager() {
		return this.manager;
	}

	/**
	 * Sets the user's role
	 * @param m A boolean representing the user's role. True for manager and false for employee
	 */
	public void setManager(boolean m) {
		this.manager = m;
	}
}
