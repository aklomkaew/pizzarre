package com.amazonaws;

import java.util.ArrayList;
import java.util.Arrays;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class OrderDb extends DatabaseTable {
	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private Table table;
	
	private String key;

	public OrderDb() throws Exception {
		tableName = "my-orders-table";
		dynamoDB = null;
		client = getClient();
		key = "userId";

		this.table = createNewTable();
		initTable();
	}
	
	public String getTableName() {
		return tableName;
	}

	public Table createNewTable() throws InterruptedException {
		dynamoDB = new DynamoDB(client);

		if (dynamoDB.getTable(tableName) != null) {
			System.out.println(tableName + " already exists."); 
			table = dynamoDB.getTable(tableName);
		} else {
			try {
				table = dynamoDB.createTable(tableName, 
						Arrays.asList(new KeySchemaElement(key, KeyType.HASH)),
						Arrays.asList(new AttributeDefinition(key, ScalarAttributeType.S)),
						new ProvisionedThroughput(10L, 10L));

				table.waitForActive();
				// define limit and do auto scaling
				System.out.println(tableName + "successfully created");
				
			} catch (Exception e) {
				System.err.println("Unable to create table: ");
				System.err.println(e.getMessage());
			}
		}
		
		return table;
	}

	public void initTable() { 
		System.out.println("\nInitializing table " + tableName);

		if(table == null) {
			System.out.println("Table is null");
			return;
		}
		
		User u = new User();
		u.setUserId(10);
		u.setName("John Doe");
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(u);
		
//		Item item = putItem(u.getUserId(), list);
//		PutItemOutcome outcome = table.putItem(item);
//		System.out.println("Put item: " + outcome + " " + item.getJSONPretty(key));
//
//		User us = new User();
//		us.setUserId(11);
//		us.setName("Bob Marley");
//		
//		list.clear();
//		list.add(us);
//		
//		item = putItem(us.getUserId(), list);
//		outcome = table.putItem(item);
//		System.out.println("Put item: " + outcome + " " + item.getJSONPretty(key));
	}

	private Item putItem(int id, ArrayList<User> list) {
		ArrayList<String> newList = new ArrayList<String>();
		newList.add(list.get(0).getName());
		return new Item().withPrimaryKey(key, id).withList("user", newList);
	}
}
