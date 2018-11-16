package com.amazonaws;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-recipe-table")
public class RecipeItem {
	private String name;
	private ArrayList<String> ingredients;

	public RecipeItem() {
		name = "";
		ingredients = new ArrayList<String>();
	}

	public RecipeItem(String n) {
		name = n;
		ingredients = new ArrayList<String>();
	}
	
	public RecipeItem(String n, ArrayList<String> list) {
		name = n;
		ingredients = list;
	}

	@DynamoDBHashKey(attributeName = "RecipeName")
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	@DynamoDBAttribute(attributeName="Ingredients")
	public ArrayList<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<String> list) {
		ingredients.addAll(list);
	}
}
