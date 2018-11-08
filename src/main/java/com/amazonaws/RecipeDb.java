package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class RecipeDb extends DatabaseTable {

	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private DynamoDBMapper mapper;
	
	public RecipeDb() throws Exception {
		tableName = "my-recipe-table";
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
		
		ArrayList<String> baseList = new ArrayList<String>();
		baseList.add("crust");
		baseList.add("sauce");
		baseList.add("cheese");
		
		RecipeItem item = new RecipeItem("basePizza", baseList);
		mapper.save(item);
		
		RecipeItem i2 = new RecipeItem("cheesePizza", baseList);
		mapper.save(i2);
		
		RecipeItem i3 = new RecipeItem("pepperoniPizza");
		i3.getIngredients().addAll(baseList);
		i3.getIngredients().add("pepperoni");
		mapper.save(i3);
		
		RecipeItem i4 = new RecipeItem("hawaiianPizza");
		i4.getIngredients().addAll(baseList);
		i4.getIngredients().add("pineapple");
		mapper.save(i4);
		
		RecipeItem i5 = new RecipeItem("vegetarianPizza");
		i5.getIngredients().addAll(baseList);
		i5.getIngredients().add("pineapple");
		i5.getIngredients().add("greenPepper");
		i5.getIngredients().add("onion");
		i5.getIngredients().add("mushroom");
		i5.getIngredients().add("spinach");
		mapper.save(i5);
	}
	
	// add item to table
	// remove item
	// set itemQuantity
	// restock
	
	public void retrieveAllItem(DynamoDBMapper mapper) {
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all recipe item");
		for (RecipeItem item : itemList) {
			System.out.print("Name = " + item.getName());
			System.out.println(" has " + item.getIngredients().size() + " ingredients: ");
			System.out.print("-->");
			for(String i : item.getIngredients()) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
}
