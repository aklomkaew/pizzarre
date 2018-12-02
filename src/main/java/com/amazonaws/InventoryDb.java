package com.amazonaws;

import java.util.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

public class InventoryDb extends DatabaseTable {

	private static String tableName;

	public InventoryDb() throws Exception {
		super();
		tableName = "my-inventory-table";

		createNewTable(tableName);
		retrieveAllItem();
	}

	public String getTableName() {
		return tableName;
	}
	
	public static void changeQuantity(String toppingName, int quantity, String option) {
		System.out.println("\nDecreasing quantity");
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(toppingName));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("IngredientName = :val1").withExpressionAttributeValues(attributeValues);

		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, scanExpression);
		
		InventoryItem item = itemList.get(0);
		
		if(option.equals("increase")) {
			item.setQuantity(item.getQuantity() + quantity);
		}
		else if(option.equals("decrease")){
			item.setQuantity(item.getQuantity() - quantity);
		}
		else {
			System.out.println("Incorrect option type");
		}
		mapper.save(item);
	}

	public static int getQuantityOfItem(String toppingName) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(toppingName));

		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("IngredientName = :val1").withExpressionAttributeValues(attributeValues);

		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, scanExpression);

		if(itemList == null || itemList.isEmpty()) {
			System.out.println("Not found");
			return -1;
		}
		
		System.out.println("Item list size = " + itemList.size());

		return itemList.get(0).getQuantity();
	}
	
	public static boolean addItem(String name, int quantity) {
		boolean status = false;
		// if order presents, then don't add it
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(name));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("IngredientName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, scanExpression);
		if(itemList == null || itemList.size() == 0) {
			InventoryItem item = new InventoryItem(name, quantity);
			mapper.save(item);
			status = true;
		}
		
		return status;
	}
	
	public static boolean deleteItem(String name) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(name));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("IngredientName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<InventoryItem> list = mapper.scan(InventoryItem.class, scanExpression);
		// should only have one item in the list
		if(list == null || list.size() < 1) {
			System.out.println("Item name " + name + " not found.");
			status = false;
		}
		else {
			mapper.delete(list.get(0));
			status = true;
		}
		
		return status;
	}

	public static List<InventoryItem> retrieveAllItem() {
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all inventory items");
		for (InventoryItem item : itemList) {
			System.out.println("Id = " + item.getName());
		}

		return itemList;
	}

	public static void restock() {
		// restock inventory, assume default quantity is 10
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		for (InventoryItem item : itemList) {
			item.setQuantity(10);
			mapper.save(item);
		}
	}
}
