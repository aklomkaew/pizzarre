package com.amazonaws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
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
	
	public void retrieveAllItem() {
		List<Cart> itemList = mapper.scan(Cart.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all users");
		for (Cart item : itemList) {
			System.out.println("Id = " + item.getOrderNumber() + " name = " + item.getServerName()
			+ " pizza name = " + item.getPizzas().get(0).getName());
		}
	}
}
