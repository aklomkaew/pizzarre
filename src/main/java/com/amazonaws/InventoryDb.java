package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class InventoryDb extends DatabaseTable {

	private static String tableName;

	public InventoryDb() throws Exception {
		super();
		tableName = "my-inventory-table";

		createNewTable(tableName);
		initTable();
		retrieveAllItem();
	}

	public String getTableName() {
		return tableName;
	}

	public static void initTable() {
		System.out.println("\nInitializing table " + tableName);
/*
		InventoryItem item = new InventoryItem();
		item.setName("cheese");
		item.setQuantity(10);
		mapper.save(item);
		
		item.setName("crust");
		item.setQuantity(10);
		mapper.save(item);
		
		item.setName("sauce");
		item.setQuantity(10);
		mapper.save(item);
		
		item.setName("pepperoni");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("sausage");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("groundBeef");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("ham");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("bacon");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("chicken");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("anchovy");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("shrimp");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("tofu");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("mushroom");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("onion");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("greenPepper");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("tomato");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("olive");
		item.setQuantity(10);
		mapper.save(item);

		item.setName("pineapple");
		item.setQuantity(10);
		mapper.save(item);
*/		
	}

	public static void decreaseQuantity(String toppingName, int quantity) {
		System.out.println("\nDecreasing quantity");

//		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
//		expressionAttributeNames.put("#q", "quantity");
//		
//		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
//		expressionAttributeValues.put(":val", quantity);
//		
//		UpdateItemOutcome outcome = table.updateItem(
//				"toppingName", toppingName,
//				"set #q = #q - :val",
//				expressionAttributeNames,
//				expressionAttributeValues);

		InventoryItem item = new InventoryItem(toppingName);
		DynamoDBQueryExpression<InventoryItem> queryExpression = new DynamoDBQueryExpression<InventoryItem>()
				.withHashKeyValues(item);
		List<InventoryItem> list = mapper.query(InventoryItem.class, queryExpression);

		for (InventoryItem i : list) {
			i.setQuantity(i.getQuantity() - quantity);
			mapper.save(i);
		}
	}

	// add item to table
	// remove item
	// set itemQuantity
	// restock

	public void retrieveAllItem() {
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all inventory item");
		for (InventoryItem item : itemList) {
			System.out.println("Topping Name = " + item.getName() + " quantity = " + item.getQuantity());
		}
	}

	public static void restock() {
		// restock inventory, assume default quantity is 10
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		for (InventoryItem item : itemList) {
			item.setQuantity(10);
		}
	}
}
