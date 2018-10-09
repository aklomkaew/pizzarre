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

public class Recipe extends DatabaseTable {

	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private Table table;

	public Recipe() throws Exception {
		tableName = "my-recipe-table";
		dynamoDB = null;
		client = getClient();

		this.table = createNewTable();
		initTable();
	}

	public Table createNewTable() throws InterruptedException {
		dynamoDB = new DynamoDB(client);

		if (dynamoDB.getTable(tableName) != null) {
			System.out.println(tableName + " already exists."); 
			table = dynamoDB.getTable(tableName);
		} else {
			try {
				table = dynamoDB.createTable(tableName, 
						Arrays.asList(new KeySchemaElement("pizzaName", KeyType.HASH)),
						Arrays.asList(new AttributeDefinition("pizzaName", ScalarAttributeType.S)),
						new ProvisionedThroughput(10L, 10L));

				table.waitForActive();
				// define limit and do auto scaling
				
			} catch (Exception e) {
				System.err.println("Unable to create table: ");
				System.err.println(e.getMessage());
			}
		}
		
		return table;
	}

	public void initTable() { 
		System.out.println("\nInitializing table " + tableName);

		if(table == null) {
			System.out.println("Table is null");
			return;
		}
		
		ArrayList<String> baseList = new ArrayList<String>();
		baseList.add("crust");
		baseList.add("sauce");
		baseList.add("cheese");
		
		Item item = putItem("basePizza", baseList);
		PutItemOutcome outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("pizzaName"));

		item = putItem("cheesePizza", baseList);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("pizzaName"));
		
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(baseList);
		list.add("pepperoni");
		
		item = putItem("pepperoniPizza", list);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("pizzaName"));
		
		list.clear();
		list.addAll(baseList);
		list.add("pineapple");
		list.add("ham");
		
		item = putItem("hawaiianPizza", list);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("pizzaName"));
		
		list.clear();
		list.addAll(baseList);
		list.add("pineapple");
		list.add("greenPepper");
		list.add("onion");
		list.add("mushroom");
		list.add("spinach");
		
		item = putItem("vegetarianPizza", list);
		outcome = table.putItem(item);
		System.out.println("Put item: " + outcome + " " + item.getJSONPretty("pizzaName"));
	}

	private Item putItem(String toppingName, ArrayList<String> list) {
		return new Item().withPrimaryKey("toppingName", toppingName).withList("toppings", list);
	}
}
