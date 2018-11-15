package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class RecipeDb extends DatabaseTable {

	private static String tableName;
	
	public RecipeDb() throws Exception {
		super();
		tableName = "my-recipe-table";

		createNewTable(tableName);
		initTable();
		retrieveAllItem();
	}
	
	public String getTableName() {
		return tableName;
	}

	public static void initTable() { 
		System.out.println("\nInitializing table " + tableName);
		
//		ArrayList<String> baseList = new ArrayList<String>();
//		baseList.add("crust");
//		baseList.add("sauce");
//		baseList.add("cheese");
//		
//		RecipeItem item = new RecipeItem("basePizza", baseList);
//		mapper.save(item);
//		
//		RecipeItem i2 = new RecipeItem("cheesePizza", baseList);
//		mapper.save(i2);
//		
//		RecipeItem i3 = new RecipeItem("pepperoniPizza");
//		i3.getIngredients().addAll(baseList);
//		i3.getIngredients().add("pepperoni");
//		mapper.save(i3);
//		
//		RecipeItem i4 = new RecipeItem("hawaiianPizza");
//		i4.getIngredients().addAll(baseList);
//		i4.getIngredients().add("pineapple");
//		mapper.save(i4);
//		
//		RecipeItem i5 = new RecipeItem("vegetarianPizza");
//		i5.getIngredients().addAll(baseList);
//		i5.getIngredients().add("pineapple");
//		i5.getIngredients().add("greenPepper");
//		i5.getIngredients().add("onion");
//		i5.getIngredients().add("mushroom");
//		i5.getIngredients().add("spinach");
//		mapper.save(i5);
	}
	
	public static void addRecipe(RecipeItem item) {
		mapper.save(item);
	}
	
	// add item to table
	// remove item
	// set itemQuantity
	// restock
	
	public static List<RecipeItem> retrieveAllItem() {
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all recipe items");
		for (RecipeItem item : itemList) {
			System.out.println("Id = " + item.getName());
		}
		
		return itemList;
	}
}
