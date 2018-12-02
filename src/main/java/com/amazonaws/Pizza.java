package com.amazonaws;

import java.util.*;

/**
 * Represents a pizza
 * @author Atchima
 *
 */
public class Pizza {
	private String name;
	private ArrayList<String> toppings;
	private int size;	
	private double price;
	private int isNew;		// 0 is false, 1 is true
	private static int NUM_BASE_TOPPINGS = 3;	// cheese, crust, sauce
	
	/**
	 * Class constructor
	 */
	public Pizza() {
		this.name = "";
		this.size = 0;
		this.toppings = new ArrayList<String>();
		this.price = 0.0;
		this.isNew = 1;
	}
	
	/**
	 * Creates a pizza with the specified name, size, and list of toppings
	 * @param n A string representing the pizza's name
	 * @param s An integer representing the pizza's size
	 * @param list An ArrayList of string representing the pizza's list of toppings
	 */
	public Pizza(String n, int s, ArrayList<String> list) {
		this.name = n;
		this.size = s;
		this.toppings = new ArrayList<String>();
		this.toppings.addAll(list);
		this.isNew = 1;
		this.price = this.toppings.size() + getPriceBySize() - NUM_BASE_TOPPINGS;
	}
	
	/**
	 * Gets the pizza's price by its size
	 * @return A double representing the pizza's price
	 */
	private double getPriceBySize() {
		double ret = 5.0;
		switch(this.size) {
		case 1: 
			ret = 5.0;
			break;
		case 2: 
			ret *= 1.75;
			break;
		case 3: 
			ret *= 2.5;
			break;
		}
		return ret;
	}
	
	/**
	 * Sets the pizza's name
	 * @param n A string representing the pizza's name
	 */
	public void setName(String n) {
		this.name = n;
	}
	
	/**
	 * Gets the pizza's name
	 * @return A string representing the pizza's name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the pizza's size
	 * @param s An integer representing the pizza's size
	 */
	public void setSize(int s) {
		this.size = s;
	}
	
	/**
	 * Gets the pizza's size
	 * @return An integer representing the pizza's size
	 */
	public int getSize() {
		return this.size;
	}
	
	public void updatePrice() {
		this.price = this.toppings.size() + getPriceBySize() - NUM_BASE_TOPPINGS;
	}
	
	/**
	 * Sets the pizza's state
	 * @param flag An integer representing the pizza's state
	 */
	public void setIsNew(int flag) {
		this.isNew = flag;
	}
	
	/**
	 * Gets the pizza's state
	 * @return An integer isNew, which returns 0 for not new and returns 1 for new
	 */
	public int getIsNew() {
		return this.isNew;
	}
	
	/**
	 * Gets the pizza's toppings
	 * @return An ArrayList of string representing the pizza's list of toppings
	 */
	public ArrayList<String> getToppings(){
		return this.toppings;
	}
	
	/**
	 * Adds the specified topping to the pizza's list of toppings
	 * @param p A string representing the name of the topping being added
	 */
	public void addTopping(String p) {
		toppings.add(p);
		addToPrice(1.0);
	}
	
	/**
	 * Sets the pizza's price
	 * @param p A double representing the pizza's price
	 */
	public void setPrice(double p) {
		this.price = p;
	}
	
	/**
	 * Gets the pizza's price
	 * @return A double representing the pizza's price
	 */
	public double getPrice() {
		return this.price;
	}
	
	/**
	 * Adds the specified price to the pizza's price
	 * @param p A double representing the price
	 */
	public void addToPrice(double p) {
		this.price += p;
	}
	
	/**
	 * Gets the pizza's information
	 * @return A string representing the information of the pizza
	 */
	public String toString() {
		String ret = "";
		
		for(String str : this.toppings) {
			ret += str + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}
}
