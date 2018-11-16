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
/*		
		 RecipeItem dailySpecialPizza = new RecipeItem("dailySpecialPizza");
		 dailySpecialPizza.getIngredients().addAll(baseList);
		 dailySpecialPizza.getIngredients().add("pepperoni");
		 dailySpecialPizza.getIngredients().add("sausage");
		 dailySpecialPizza.getIngredients().add("groundBeef");
		 dailySpecialPizza.getIngredients().add("onion");
		 dailySpecialPizza.getIngredients().add("mushroom");
		 dailySpecialPizza.getIngredients().add("olive");
		 mapper.save(dailySpecialPizza);
		 
		 RecipeItem meatzzaPizza = new RecipeItem("meatzzaPizza");
		 meatzzaPizza.getIngredients().addAll(baseList);
		 meatzzaPizza.getIngredients().add("pepperoni");
		 meatzzaPizza.getIngredients().add("sausage");
		 meatzzaPizza.getIngredients().add("groundBeef");
		 meatzzaPizza.getIngredients().add("ham");
		 mapper.save(meatzzaPizza);
		 
		 RecipeItem hawaiianPizza = new RecipeItem("hawaiianPizza");
		 hawaiianPizza.getIngredients().addAll(baseList);
		 hawaiianPizza.getIngredients().add("bacon");
		 hawaiianPizza.getIngredients().add("pineapple");
		 hawaiianPizza.getIngredients().add("ham");
		 mapper.save(hawaiianPizza);
		 
		 RecipeItem classicPizza = new RecipeItem("classicPizza");
		 classicPizza.getIngredients().addAll(baseList);
		 classicPizza.getIngredients().add("pepperoni");
		 classicPizza.getIngredients().add("onion");
		 classicPizza.getIngredients().add("greenPepper");
		 classicPizza.getIngredients().add("mushroom");
		 mapper.save(classicPizza);
		 
		 RecipeItem veggiePizza = new RecipeItem("veggiePizza");
		 veggiePizza.getIngredients().addAll(baseList);
		 veggiePizza.getIngredients().add("mushroom");
		 veggiePizza.getIngredients().add("tomato");
		 veggiePizza.getIngredients().add("onion");
		 veggiePizza.getIngredients().add("greenPepper");
		 veggiePizza.getIngredients().add("olive");
		 mapper.save(veggiePizza);
		 
		 RecipeItem sicilianPizza = new RecipeItem("sicilianPizza");
		 sicilianPizza.getIngredients().addAll(baseList);
		 sicilianPizza.getIngredients().add("anchovy");
		 sicilianPizza.getIngredients().add("onion");
		 sicilianPizza.getIngredients().add("tomato");
		 mapper.save(sicilianPizza);
*/
	}
	
	public static void addRecipe(RecipeItem item) {
		mapper.save(item);
	}
	
	// add item to table
	// remove item
	// set itemQuantity
	// restock
	
	public void retrieveAllItem() {
		List<RecipeItem> itemList = mapper.scan(RecipeItem.class, new DynamoDBScanExpression());

		System.out.println("\nRetrieving all recipe item");
		for (RecipeItem item : itemList) {
			System.out.print("Name = " + item.getName());
			System.out.println(" has " + item.getIngredients().size() + " ingredients: ");
			System.out.print("--> ");
			for(String i : item.getIngredients()) {
				System.out.print(i + " ");
			}
			System.out.println();
		}
	}
}
