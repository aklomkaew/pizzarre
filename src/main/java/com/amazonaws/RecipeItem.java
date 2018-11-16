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
		this.name = "";
		this.ingredients = new ArrayList<String>();
	}

	public RecipeItem(String n) {
		this.name = n;
		this.ingredients = new ArrayList<String>();
	}
	
	public RecipeItem(String n, ArrayList<String> list) {
		this.name = n;
		this.ingredients = list;
	}

	@DynamoDBHashKey(attributeName = "RecipeName")
	public String getName() {
		return this.name;
	}

	public void setName(String n) {
		this.name = n;
	}

	@DynamoDBAttribute(attributeName="Ingredients")
	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}

	public void setIngredients(ArrayList<String> list) {
		this.ingredients.addAll(list);
	}
}
