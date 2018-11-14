package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "my-inventory-table")
public class InventoryItem {
	private String name;
	private int quantity;

	public InventoryItem() {
	}

	public InventoryItem(String n, int q) {
		name = n;
		quantity = q;
	}

	public InventoryItem(String n) {
		name = n;
	}
	
	@DynamoDBHashKey(attributeName = "Name")
	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	@DynamoDBAttribute(attributeName="Quantity")
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int n) {
		quantity = n;
	}
}
