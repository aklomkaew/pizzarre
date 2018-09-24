package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public class Inventory_old extends DatabaseTable {

	// private DynamoDB dynamoDB;

	private String tableName;

	private Table table;

	// private AmazonDynamoDB client;
	private AmazonDynamoDB dynamoDB;

	public Inventory_old() throws Exception {
		// tableName = "my-inventory-table";
		initDb();

		dynamoDB = getClient();
		// dynamoDB = new DynamoDB(client);
		// table = dynamoDB.getTable(tableName);

		initInventory();
	}

	private void initInventory() throws Exception {
//		DynamoDB d = new DynamoDB(dynamoDB);
//		table = d.getTable("my-favorite-movies-table");
//		
//		try {
//			table.delete();
//			table.waitForDelete();
//			System.out.println("Successfully deleted");
//		}
//		catch(Exception e) {
//			System.err.println("Unable to delete");
//			System.err.println(e.getMessage());
//		}
		
		try {
			String tableName = "my-favorite-movies-table";

			// Create a table with a primary hash key named 'name', which holds a string
			CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
					.withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
					.withAttributeDefinitions(new AttributeDefinition().withAttributeName("name")
							.withAttributeType(ScalarAttributeType.S))
					.withProvisionedThroughput(
							new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));

			// Create table if it does not exist yet
			TableUtils.createTableIfNotExists(dynamoDB, createTableRequest);
			// wait for the table to move into ACTIVE state
			TableUtils.waitUntilActive(dynamoDB, tableName);

			// Describe our new table
			DescribeTableRequest describeTableRequest = new DescribeTableRequest().withTableName(tableName);
			TableDescription tableDescription = dynamoDB.describeTable(describeTableRequest).getTable();
			System.out.println("Table Description: " + tableDescription);

			// Add an item
			Map<String, AttributeValue> item = newItem("crust", 10);
			PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
			PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
			System.out.println("Result: " + putItemResult);

			// Add another item
			item = newItem("pepperoni", 10);
			putItemRequest = new PutItemRequest(tableName, item);
			putItemResult = dynamoDB.putItem(putItemRequest);
			System.out.println("Result: " + putItemResult);

			// Scan items for movies with a year attribute greater than 1985
			HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
			Condition condition = new Condition().withComparisonOperator(ComparisonOperator.GT.toString())
					.withAttributeValueList(new AttributeValue().withN("1985"));
			scanFilter.put("year", condition);
			ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
			ScanResult scanResult = dynamoDB.scan(scanRequest);
			System.out.println("Result: " + scanResult);

			// Name | Quantity
//			Item item = newItem2("crust", 10);
//			table.putItem(item);
//
//			item = newItem2("sauce", 10);
//			table.putItem(item);
//			
//			item = newItem2("cheese", 10);
//			table.putItem(item);
//
//			item = newItem2("pepperoni", 10);
//			table.putItem(item);
//
//			item = newItem2("greenPepper", 10);
//			table.putItem(item);

//			CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
//					.withKeySchema(new KeySchemaElement().withAttributeName("name").withKeyType(KeyType.HASH))
//					.withAttributeDefinitions(new AttributeDefinition().withAttributeName("name")
//							.withAttributeType(ScalarAttributeType.S))
//					.withProvisionedThroughput(
//							new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
//
//			// Create table if it does not exist yet
//			TableUtils.createTableIfNotExists(client, createTableRequest);
//			// wait for the table to move into ACTIVE state
//			TableUtils.waitUntilActive(client, tableName);
//
//			// Add an item
//			Map<String, AttributeValue> item = newItem("crust", 10);
//			PutItemRequest putItemRequest = new PutItemRequest(tableName, item);
//			PutItemResult putItemResult = client.putItem(putItemRequest);
//			System.out.println("Result: " + putItemResult);
//
//			// Add another item
//			item = newItem("pepperoni", 10);
//			putItemRequest = new PutItemRequest(tableName, item);
//			putItemResult = client.putItem(putItemRequest);
//			System.out.println("Result: " + putItemResult);

		} catch (AmazonServiceException ase) {
			System.out.println("Caught an AmazonServiceException, which means your request made it "
					+ "to AWS, but was rejected with an error response for some reason.");
			System.out.println("Error Message:    " + ase.getMessage());
			System.out.println("HTTP Status Code: " + ase.getStatusCode());
			System.out.println("AWS Error Code:   " + ase.getErrorCode());
			System.out.println("Error Type:       " + ase.getErrorType());
			System.out.println("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			System.out.println("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with AWS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message: " + ace.getMessage());
		}
	}

	public void printTable() {
//		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
//		ScanResult result = client.scan(scanRequest);
//
//		System.out.println("Topping Name\tQuantity");
//		for (Map<String, AttributeValue> item : result.getItems()) {
//			System.out.println(item);
//		}
		ScanRequest scanRequest = new ScanRequest().withTableName(tableName);
		ScanResult result = dynamoDB.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			System.out.println(item);
		}

	}

	public void decreaseQuantity(String toppingName, int quantity) {
		UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("toppingName", toppingName)
				.withUpdateExpression("set info.quantity = info.quantity - :val")
				.withValueMap(new ValueMap().withNumber(":val", quantity)).withReturnValues(ReturnValue.UPDATED_NEW);

		try {
			UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
			System.out.println("UpdateItem succeeded:\n" + outcome.getItem().toJSONPretty());
		} catch (Exception e) {
			System.err.println("Unable to update item: " + toppingName + " ");
			System.err.println(e.getMessage());
		}
	}

	private Item newItem2(String toppingName, int quantity) {
		return new Item().withString("toppingName", toppingName).withNumber("quantity", quantity);
	}

	private Map<String, AttributeValue> newItem(String toppingName, int quantity) {
		Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
		item.put("name", new AttributeValue(toppingName));
		item.put("quantity", new AttributeValue().withN(Integer.toString(quantity)));

		return item;
	}
}
