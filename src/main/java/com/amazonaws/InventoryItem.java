package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

/**
 * Represents an inventory item stored in the inventory database
 * @author Atchima
 *
 */
@DynamoDBTable(tableName = "my-inventory-table")
public class InventoryItem {
	private String name;
	private int quantity;

	/**
	 * Class constructor
	 */
	public InventoryItem() {
		name = "";
		quantity = -1;
	}

	/**
	 * Creates an inventory item with the specified name and quantity
	 * @param n A string representing the inventory item's name
	 * @param q An integer representing the inventory item's quantity
	 */
	public InventoryItem(String n, int q) {
		name = n;
		quantity = q;
	}

	/**
	 * Creates an inventory item with the specified name
	 * @param n
	 */
	public InventoryItem(String n) {
		name = n;
	}
	
	/**
	 * Gets the inventory item's name
	 * @return A string representing the inventory item's name
	 * Sets the attribute name of the inventory item's name on DynamoDB 
	 */
	@DynamoDBHashKey(attributeName = "IngredientName")
	public String getName() {
		return name;
	}

	/**
	 * Sets the inventory item's name
	 * @param n A string representing the inventory item's name
	 */
	public void setName(String n) {
		name = n;
	}

	/**
	 * Gets the inventory item's quantity
	 * @return An integer representing the inventory item's quantity
	 * Sets the attribute name of the inventory item's quantity on DynamoDB
	 */
	@DynamoDBAttribute(attributeName="Quantity")
	public int getQuantity() {
		return quantity;
	}

	/**
	 * Sets the inventory item's quantity
	 * @param n An integer representing the inventory item's quantity
	 */
	public void setQuantity(int n) {
		quantity = n;
	}
}
