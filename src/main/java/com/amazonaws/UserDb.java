package com.amazonaws;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

/**
 * Represents the User Database
 * @author Atchima
 *
 */
public class UserDb extends DatabaseTable {

	private static String tableName;

	/**
	 * Class constructor, calls its base class DatabaseTable
	 * @throws Exception
	 */
	public UserDb() throws Exception {
		super();
		tableName = "my-users-table";

		createNewTable(tableName);
		retrieveAllItem();
	}

	/**
	 * Gets the name of the database table
	 * @return A string representing the database's table name
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Adds a user to the User Database with the specified user's ID and User object
	 * @param id An integer representing the user's ID
	 * @param u A User representing a User object
	 * @return True if the User object can be added, false otherwise
	 * If the User object with the specified ID already exists in the User Database,
	 * then the User object cannot be added
	 */
	public static boolean addUser(int id, User u) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(id)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("Id = :val1").withExpressionAttributeValues(attributeValues);
		
		List<User> list = mapper.scan(User.class, scanExpression);
		if(list == null || list.size() < 1) {
			mapper.save(u);
			status = true;
		}
		else {
			System.out.println("Id is taken");
			status = false;
		}
		
		return status;
	}
	
	/**
	 * Updates the existing User object in the User Database
	 * @param u A User object representing the object to be updated in the User Database
	 */
	public static void updateUser(User u) {
		mapper.save(u);
	}
	
	/**
	 * Deletes a user from the User Database with the specified ID
	 * @param id An integer representing the user's ID
	 * @return True if the user can be deleted, false otherwise
	 * If the user is not found in the User Database,
	 * then the user cannot be deleted
	 */
	public static boolean deleteUser(int id) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(id)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("Id = :val1").withExpressionAttributeValues(attributeValues);
		
		List<User> list = mapper.scan(User.class, scanExpression);
		// should only have one item in the list
		if(list == null || list.size() < 1) {
			System.out.println("User with id " + id + "not found.");
			status = false;
		}
		else {
			mapper.delete(list.get(0));
			status = true;
		}
		
		return status;
	}

	/**
	 * Retrieves all User object that exists in the User Database
	 * @return A list of User representing all User object in the User Database
	 */
	public static List<User> retrieveAllItem() {
		List<User> itemList = mapper.scan(User.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all users");
		for (User item : itemList) {
			System.out.println("Id = " + item.getUserId() + " name = " + item.getName());
		}
		
		return itemList;
	}

	/**
	 * Gets the User object from the specified passcode
	 * @param password A string representing the user's passcode
	 * @return A generic type of User depending on the type of user specified by the passcode.
	 * Returns Manager if the user is a manager, User if the user is an employee, and null
	 * if the user is not found
	 */
	@SuppressWarnings("unchecked")
	public static <T extends User> T getUser(String passcode) {
		boolean manager = false;
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(passcode));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("Passcode = :val1").withExpressionAttributeValues(attributeValues);
		
		List<User> userList = null;
		List<Manager> managerList = null;
		try {
			managerList = mapper.scan(Manager.class, scanExpression);
			manager = true;
		}
		catch(Exception e) {
			System.err.println("Not manager");
			try {
				userList = mapper.scan(User.class, scanExpression);
			}
			catch(Exception ex) {
				System.err.println("Not user");
			}
		}
		
		if(manager) {
			Manager man = new Manager();
			if(managerList == null || managerList.size() < 1) {
				return (T) man;
			}
			man = managerList.get(0);
			return (T) man;
		}
		
		User u = new User();
		if(userList == null || userList.size() < 1) {
			return (T) u;
		}
		
		u = userList.get(0);
		return (T) u;
	}
}
