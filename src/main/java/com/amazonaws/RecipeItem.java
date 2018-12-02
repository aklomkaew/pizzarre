package com.amazonaws;

import java.util.ArrayList;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents a recipe
 * 
 * @author Atchima
 *
 */
@DynamoDBTable(tableName = "my-recipe-table")
public class RecipeItem {
	private String name;
	private ArrayList<String> ingredients;

	/**
	 * Class constructor
	 */
	public RecipeItem() {
		this.name = "";
		this.ingredients = new ArrayList<String>();
	}

	/**
	 * Creates a recipe item with the specified name and list of toppings
	 * 
	 * @param n    A string representing the recipe's name
	 * @param list An ArrayList of string representing the list of toppings
	 */
	public RecipeItem(String n, ArrayList<String> list) {
		this.name = n;
		this.ingredients = list;
	}

	/**
	 * Gets the recipe's name
	 * 
	 * @return A string representing the recipe's name
	 */
	@DynamoDBHashKey(attributeName = "RecipeName")
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the recipe's name
	 * 
	 * @param n A string representing the recipe's name
	 */
	public void setName(String n) {
		this.name = n;
	}

	/**
	 * Gets the recipe's ingredients
	 * 
	 * @return An ArrayList of string representing the recipe's ingredients
	 */
	@DynamoDBAttribute(attributeName = "Ingredients")
	public ArrayList<String> getIngredients() {
		return this.ingredients;
	}

	/**
	 * Sets the recipe's ingredients
	 * 
	 * @param list An ArrayList of string representing the recipe's ingredients
	 */
	public void setIngredients(ArrayList<String> list) {
		for (String str : list) {
			this.ingredients.add(str.toLowerCase());
		}
	}

	/**
	 * Gets the string of all ingredient in the recipe
	 * 
	 * @return A string representing all ingredient in the recipe
	 */
	public String toString() {
		String ret = "";
		for (String str : this.ingredients) {
			ret += str + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}
}
