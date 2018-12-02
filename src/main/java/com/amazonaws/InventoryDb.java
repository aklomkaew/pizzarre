package com.amazonaws;

import java.util.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * Represents the Inventory Database
 * @author Atchima
 *
 */
public class InventoryDb extends DatabaseTable {

	private static String tableName;
	private static int DEFAULT_QUANTITY = 10;

	/**
	 * Class constructor, calls its base class DatabaseTable
	 * @throws Exception
	 */
	public InventoryDb() throws Exception {
		super();
		tableName = "my-inventory-table";

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
	 * Changes the quantity of the specified toppingName with the specified quantity in the Inventory Database. 
	 * The change is specified by the option
	 * @param toppingName A string representing the inventory item's toppingName
	 * @param quantity An integer representing the inventory item's quantity
	 * @param option A string representing how the quantity is changed
	 */
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

	/**
	 * Gets the quantity of the specified toppingName
	 * @param toppingName A string representing the inventory item's name
	 * @return An integer representing the quantity of the toppingName if valid
	 * If the toppingName is not found in the database, returns -1
	 */
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
	
	/**
	 * Adds an inventory item to the Inventory Database with the specified name and quantity
	 * @param name A string representing the inventory item's name
	 * @param quantity An integer representing the inventory item's quantity
	 * @return True if the inventory item can be added, false otherwise
	 * If the inventory item already exists in the Inventory Database,
	 * then the inventory item cannot be added
	 */
	public static boolean addItem(String name, int quantity) {
		boolean status = false;
		
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
	
	/**
	 * Deletes an inventory item from the Inventory Database with the specified name
	 * @param name A string representing the inventory item's name
	 * @return True if the inventory item can be deleted, false otherwise
	 * If the inventory item is not found in the Inventory Database,
	 * then the inventory item cannot be deleted
	 */
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

	/**
	 * Retrieves all inventory item that exists in the Inventory Database
	 * @return A list of InventoryItem representing all InventoryItem in the Inventory Database
	 */
	public static List<InventoryItem> retrieveAllItem() {
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all inventory items");
		for (InventoryItem item : itemList) {
			System.out.println("Id = " + item.getName());
		}

		return itemList;
	}

	/**
	 * Restocks the Inventory Database to the default quantity
	 */
	public static void restock() {
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		for (InventoryItem item : itemList) {
			item.setQuantity(DEFAULT_QUANTITY);
			mapper.save(item);
		}
	}
}
