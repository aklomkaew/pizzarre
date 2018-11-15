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

	public static boolean addUser(int i, User u) {
		boolean status = false;
		
		User tmp = new User();
		tmp.setUserId(i);
		DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>();
		List<User> itemList = mapper.query(User.class, queryExpression);
		// not gonna care about manager
		// not going to add same id
		
		if(itemList.size() == 0) {
			mapper.save(u);
			status = true;
		}
		// if status == false, then that ID is taken
		
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
