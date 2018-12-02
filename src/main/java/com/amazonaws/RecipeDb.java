package com.amazonaws;

import java.util.*;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

/**
 * Represents the Recipe Database
 * @author Atchima
 *
 */
public class RecipeDb extends DatabaseTable {

	private static String tableName;
	
	/**
	 * Class constructor, calls its base class DatabaseTable
	 * @throws Exception
	 */
	public RecipeDb() throws Exception {
		super();
		tableName = "my-recipe-table";

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
	 * Adds a recipe to the Recipe Database with the specified Recipe object
	 * @param item A RecipeItem object representing the recipe to be added to the Recipe Database
	 * @return True if the RecipeItem object can be added, false otherwise
	 * If the RecipeItem object already exists in the Recipe Database,
	 * then the RecipeItem object cannot be added
	 */
	public static boolean addRecipe(RecipeItem item) {
		boolean status = false;
		
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(item.getName()));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("RecipeName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, scanExpression);
		if(itemList == null || itemList.size() == 0) {
			mapper.save(item);
			status = true;
		}
		
		return status;
	}
	
	/**
	 * Deletes a recipe from the Recipe Database with the specified recipe's name
	 * @param n A string representing the recipe's name
	 * @return True if the RecipeItem object can be deleted, false otherwise
	 * If the RecipeItem object is not found in the Recipe Database,
	 * then the RecipeItem object cannot be deleted
	 */
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
	
	/**
	 * Retrieves all RecipeItem object that exists in the Recipe Database
	 * @return A list of RecipeItem representing all RecipeItem object in the Recipe Database
	 */
	public static List<RecipeItem> retrieveAllItem() {
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all recipe items");
		for (RecipeItem item : itemList) {
			System.out.println("Id = " + item.getName());
		}
		
		return itemList;
	}
	
	/**
	 * Gets all ingredients for the specified recipe's name
	 * @param name A string representing the recipe's name
	 * @return An ArrayList of string representing the list of ingredients
	 */
	public static ArrayList<String> getIngredients(String name) {
		Map<String, AttributeValue> attributeValues = new HashMap<String, AttributeValue>();
		attributeValues.put(":val1", new AttributeValue().withS(name));
		
		DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
				.withFilterExpression("RecipeName = :val1").withExpressionAttributeValues(attributeValues);
		
		List<RecipeItem> recipeItem = mapper.scan(RecipeItem.class, scanExpression);
		
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(recipeItem.get(0).getIngredients());
		
		return ret;
	}
}
