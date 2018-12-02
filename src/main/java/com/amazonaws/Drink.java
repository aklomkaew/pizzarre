package com.amazonaws;

public class Drink {
	private static double DRINK_PRICE = 2;
	private String name;
	private double price;
	private int isNew;		// 0 is false, 1 is true
	
	/**
	 * Class constructor
	 * Assigns the variable isNew to 1, or true
	 */
	public Drink() {
		this.name = "";
		this.price = 0.0;
		isNew = 1;
	}
	
	/**
	 * Creates a drink with specified name and price
	 * Assigns the variable isNew to 1, or true
	 * @param n A string representing the drink's name
	 * @param p A double representing the drink's price
	 */
	public Drink(String n, double p) {
		this.name = n;
		this.price = p;
		isNew = 1;
	}
	
	/**
	 * Sets the drink's name
	 * @param n A string representing the drink's name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Gets the drink's name
	 * @return A string representing the drink's name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Sets drink's isNew status to 0, or false
	 */
	public void setIsNew() {
		this.isNew = 0;
	}
	
	/**
	 * Gets drink's isNew status
	 * @return An integer isNew, which returns 0 for not new and returns 1 for new
	 */
	public int getIsNew() {
		return this.isNew;
	}
	
	/**
	 * Sets the drink's price
	 */
	public void setPrice() {
		this.price = DRINK_PRICE;
	}
	
	/**
	 * Gets the drink's price
	 * @return A double representing the drink's price
	 */
	public double getPrice() {
		return this.price;
	}
}
