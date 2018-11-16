package com.amazonaws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;

public class OrderDb extends DatabaseTable {

	private static String tableName;

	public OrderDb() throws Exception {
		super();
		tableName = "my-orders-table";

		createNewTable(tableName);
		//initTable();
		retrieveAllItem();
	}

	public String getTableName() {
		return tableName;
	}

	public static void initTable() {
		System.out.println("\nInitializing table " + tableName);
		
		Pizza p = new Pizza();
		p.setName("testPizza");
		List<Pizza> list = new ArrayList<Pizza>(Arrays.asList(p));
		
		Order c = new Order(1, list);
		c.setServerName("server1");
		c.setTotal(100);
		mapper.save(c);
	}

	public static boolean addOrder(Order c) {
		boolean status = false;
		// if order presents, then don't add it
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(c.getOrderNumber())));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("OrderNumber = :val1").withExpressionAttributeValues(attributeValues);
		
		List<Order> itemList = mapper.scan(Order.class, scanExpression);
		if(itemList == null || itemList.size() == 0) {
			mapper.save(c);
			status = true;
		}
		
		return status;
	}
	
	public static void updateOrder(Order obj) {
		mapper.save(obj);
	}
	
	public static ArrayList<Order> retrieveAllItem() {
		List<Order> itemList = mapper.scan(Order.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all orders");
		for (Order item : itemList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName()
			+ " total = " + item.getTotal());
		}
		
		ArrayList<Order> ret = new ArrayList<Order>();
		ret.addAll(itemList);
		
		return ret;
	}
	
	public static List<Order> retrieveFilteredItem(int id) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(id)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("ServerId = :val1").withExpressionAttributeValues(attributeValues);
		
		List<Order> itemList = mapper.scan(Order.class, scanExpression);
		
		return itemList;
	}
	
}