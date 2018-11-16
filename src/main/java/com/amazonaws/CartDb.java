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

public class CartDb extends DatabaseTable {

	private static String tableName;

	public CartDb() throws Exception {
		super();
		tableName = "my-carts-table";

		createNewTable(tableName);
		initTable();
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
		
		Cart c = new Cart(1, list);
		c.setServerName("server1");
		c.setTotal(100);
		mapper.save(c);
	}

	public static void addCart(Cart c) {
		mapper.save(c);
	}
	
	public static List<Cart> retrieveAllItem() {
		List<Cart> itemList = mapper.scan(Cart.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all carts");
		for (Cart item : itemList) {
			System.out.println("Id = " + item.getOrderNumber() + " server name = " + item.getServerName()
			+ " total = " + item.getTotal());
		}
		
		return itemList;
	}
	
	public static List<Cart> retrieveFilteredItem(int id) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(id)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("ServerId = :val1").withExpressionAttributeValues(attributeValues);
		
		List<Cart> itemList = mapper.scan(Cart.class, scanExpression);
		
		return itemList;
	}
	
}
