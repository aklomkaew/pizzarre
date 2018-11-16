package com.amazonaws;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
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
	
	public static void addRecipe(RecipeItem item) {
		mapper.save(item);
	}
	
	// add item to table
	// remove item
	// set itemQuantity
	// restock
	
	public static boolean deleteItem(String n) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(n));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("RecipeName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<RecipeItem> list = mapper.scan(RecipeItem.class, scanExpression);
		// should only have one item in the list
		if(list == null || list.size() < 1) {
			System.out.println("Recipe name " + n + " is not found.");
			status = false;
		}
		else {
			mapper.delete(list.get(0));
			status = true;
		}
		
		return status;
	}
	
	public static List<RecipeItem> retrieveAllItem() {
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all recipe items");
		for (RecipeItem item : itemList) {
			System.out.println("Id = " + item.getName());
		}
		
		return itemList;
	}
	
	public static ArrayList<String> getIngredients(String name) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(name));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("RecipeName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<RecipeItem> recipeItem = mapper.scan(RecipeItem.class, scanExpression);
		
		// should be only one recipe
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(recipeItem.get(0).getIngredients());
		
		return ret;
	}
}
