package com.amazonaws;

import java.util.*;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public class Inventory extends DatabaseTable {

	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private Table table;

	public Inventory() throws Exception {
		tableName = "my-inventory-table";
		dynamoDB = null;
		client = getClient();

		this.table = createNewTable();
		initTable();
	}

	public Table createNewTable() throws InterruptedException {
		dynamoDB = new DynamoDB(client);

		if (dynamoDB.getTable(tableName) != null) {
			System.out.println(tableName + " already exists."); // Deleting...");
			table = dynamoDB.getTable(tableName);
			// deleteTable();
		} else {
			try {
				table = dynamoDB.createTable(tableName, 
						Arrays.asList(new KeySchemaElement("toppingName", KeyType.HASH)),
						Arrays.asList(new AttributeDefinition("toppingName", ScalarAttributeType.S)),
						new ProvisionedThroughput(10L, 10L));

//				List<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();
//				attributeDefinitions
//						.add(new AttributeDefinition().withAttributeName("toppingName").withAttributeType("S"));
//
//				List<KeySchemaElement> keySchema = new ArrayList<KeySchemaElement>();
//				keySchema.add(new KeySchemaElement().withAttributeName("toppingName").withKeyType(KeyType.HASH));

//				CreateTableRequest request = new CreateTableRequest().withTableName(tableName).withKeySchema(keySchema)
//						.withAttributeDefinitions(attributeDefinitions).withProvisionedThroughput(
//								new ProvisionedThroughput().withReadCapacityUnits(10L).withWriteCapacityUnits(10L));
//
//				table = dynamoDB.createTable(request);

				table.waitForActive();
				// define limit and do auto scaling
				
			} catch (Exception e) {
				System.err.println("Unable to create table: ");
				System.err.println(e.getMessage());
			}
		}
		
		return table;
	}

	public void initTable() { //Table tableToAdd) {
		System.out.println("\nInitializing table " + tableName);

		if(table == null) {
			System.out.println("Table is null");
			return;
		}
		
		Item item = putItem("crust", 10);
		PutItemOutcome outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("toppingName"));

		item = putItem("cheese", 10);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("toppingName"));
		
		item = putItem("sauce", 10);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("toppingName"));
		
		item = putItem("pepperoni", 10);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("toppingName"));
		
		item = putItem("greenPepper", 10);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("toppingName"));
	}

	private Item putItem(String toppingName, int quantity) {
		return new Item().withPrimaryKey("toppingName", toppingName).withNumber("quantity", quantity);
	}
	
	public void decreaseQuantity(String toppingName, int quantity) {
		System.out.println("\nDecreasing quantity");
		
		if(table == null) {
			System.out.println("table is null");
			return;
		}
		
//		if(table.getItem(toppingName) == null) {
//			System.out.println(toppingName + " is not in inventory");
//			return;
//		}
		
		Map<String, String> expressionAttributeNames = new HashMap<String, String>();
		expressionAttributeNames.put("#q", "quantity");
		
		Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
		expressionAttributeValues.put(":val", quantity);
		
		UpdateItemOutcome outcome = table.updateItem(
				"toppingName", toppingName,
				"set #q = #q - :val",
				expressionAttributeNames,
				expressionAttributeValues);
	}
}
