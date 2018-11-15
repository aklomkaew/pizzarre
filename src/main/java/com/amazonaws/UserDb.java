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

public class UserDb extends DatabaseTable {

	private static String tableName;

	public UserDb() throws Exception {
		super();
		tableName = "my-users-table";

		createNewTable(tableName);
		initTable();
		retrieveAllItem();
	}

	public String getTableName() {
		return tableName;
	}

	public static void initTable() {
		System.out.println("\nInitializing table " + tableName);
		
		Manager m = new Manager(0, "Manager Smith");
		mapper.save(m);
		System.out.println("Save manager successfully");
		
		User u = new User(1, "User");
		mapper.save(u);
		System.out.println("Save user successfully");
		
//		Manager m = new Manager();
//		m.setName("Main Boo");
//		m.setUserId(0);
//		mapper.save(m);
//		System.out.println("Save manager successful");
		
//		User u = new User();
//		u.setName("John Doe");
//		u.setUserId(1);
//		mapper.save(u);
//
//		u.setName("Bob Marley");
//		u.setUserId(2);
//		mapper.save(u);
//
//		u.setName("Mary Allen");
//		u.setUserId(3);
//		mapper.save(u);
	}

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
			// do alert that the id is taken
			System.out.println("Id is taken");
			status = false;
		}
		
		return status;
	}
	
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

	public static void retrieveAllItem() {
		List<User> itemList = mapper.scan(User.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all users");
		for (User item : itemList) {
			System.out.println("Id = " + item.getUserId() + " name = " + item.getName());
		}
	}

	public static <T extends User> T getUser(String password) {
		boolean manager = false;
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(password));
		
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
