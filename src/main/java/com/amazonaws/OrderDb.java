package com.amazonaws;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * Represents the Order Database
 * @author Atchima
 *
 */
public class OrderDb extends DatabaseTable {

	private static String tableName;

	/**
	 * Class constructor, calls its base class DatabaseTable
	 * @throws Exception
	 */
	public OrderDb() throws Exception {
		super();
		tableName = "my-orders-table";

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
	 * Adds an order to the Order Database with the specified Order object
	 * @param c An Order object representing the order to be added to the Order Database
	 * @return True if the Order object can be added, false otherwise
	 * If the Order object's orderNumber already exists in the Order Database,
	 * then the Order object cannot be added
	 */
	public static boolean addOrder(Order c) {
		boolean status = false;
		
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
	
	/**
	 * Deletes an order from the Order Database with the specified orderNumber
	 * @param num An integer representing the orderNumber
	 * @return True if the Order object can be deleted, false otherwise
	 * If the Order object is not found in the Order Database,
	 * then the Order object cannot be deleted
	 */
	public static boolean deleteItem(int num) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(num)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("OrderNumber = :val1").withExpressionAttributeValues(attributeValues);
		
		List<Order> list = mapper.scan(Order.class, scanExpression);
		// should only have one item in the list
		if(list == null || list.size() < 1) {
			System.out.println("Order id " + num + " is not found.");
			status = false;
		}
		else {
			mapper.delete(list.get(0));
			status = true;
		}
		
		return status;
	}
	
	/**
	 * Updates the existing Order object in the Order Database
	 * @param obj An Order object representing the object to be updated in the Order Database
	 */
	public static void updateOrder(Order obj) {
		mapper.save(obj);
	}
	
	/**
	 * Retrieves all Order object that exists in the Order Database
	 * @return A list of Order representing all Order object in the Order Database
	 */
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
	
	/**
	 * Retrieves all Order object that exists in the Order Database filtered by the specified orderNumber
	 * @param id An integer representing the orderNumber 
	 * @return A list of Order representing all filtered Order object in the Order Database
	 */
	public static List<Order> retrieveFilteredItem(int id) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(id)));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("ServerId = :val1").withExpressionAttributeValues(attributeValues);
		
		List<Order> itemList = mapper.scan(Order.class, scanExpression);
		
		return itemList;
	}
	
}
