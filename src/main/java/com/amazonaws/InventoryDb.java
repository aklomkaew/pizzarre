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

	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private DynamoDBMapper mapper;
	
	public InventoryDb() throws Exception {
		tableName = "my-inventory-table";
		dynamoDB = null;
		client = getClient();

		mapper = createNewTable();
		initTable(mapper);
		retrieveAllItem(mapper);
	}
	
	public String getTableName() {
		return tableName;
	}

	public DynamoDBMapper createNewTable() throws InterruptedException {
		dynamoDB = new DynamoDB(client);
		DynamoDBMapper mapper = new DynamoDBMapper(client);

		if (dynamoDB.getTable(tableName) != null) {
			System.out.println(tableName + " already exists.");
		} else {
			mapper = new DynamoDBMapper(client);
			CreateTableRequest req = mapper.generateCreateTableRequest(InventoryItem.class);
			req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
			dynamoDB.createTable(req);
		}

		return mapper;
	}

	public void initTable(DynamoDBMapper mapper) {
		System.out.println("\nInitializing table " + tableName);

		if (mapper == null) {
			System.out.println("mapper is null");
			return;
		}

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
		
		item.setName("greenPepper");
		item.setQuantity(10);
		mapper.save(item);
	}
	
	public void decreaseQuantity(String toppingName, int quantity) {
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
		DynamoDBQueryExpression<InventoryItem> queryExpression = new DynamoDBQueryExpression<InventoryItem>().withHashKeyValues(item);
		List<InventoryItem> list = mapper.query(InventoryItem.class, queryExpression);
		
		for(InventoryItem i : list) {
			i.setQuantity(i.getQuantity() - quantity);
			mapper.save(i);
		}
	}
	
	// add item to table
	// remove item
	// set itemQuantity
	// restock
	
	public void retrieveAllItem(DynamoDBMapper mapper) {
		List<InventoryItem> itemList = mapper.scan(InventoryItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all inventory item");
		for (InventoryItem item : itemList) {
			System.out.println("Topping Name = " + item.getName() + " quantity = " + item.getQuantity());
		}
	}
}
