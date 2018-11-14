package com.amazonaws;

import java.util.*;

public class Pizza {
	private String name;
	private HashMap<String, Integer> toppings;
	private int size;	// S = 1, M = 2, L = 3
	
	public Pizza() {
		this.name = "";
		this.size = 0;
		this.toppings = new HashMap<String, Integer>();
	}
	
	public Pizza(String n, int s, HashMap<String, Integer> map) {
		this.name = n;
		this.size = s;
		this.toppings = map;
	}
	
	public void setName(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setSize(int s) {
		this.size = s;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public void addTopping(String item, int quantity) {
		if(!toppings.containsKey(item)) {
			toppings.put(item,  quantity * size);
		}
		else {
			toppings.put(item, toppings.get(item) + (quantity * size));
		}
	}
	
	public void clearToppings() {
		this.toppings.clear();
	}
	
	public HashMap<String, Integer> getToppings(){
		return this.toppings;
	}
	
	public void deleteTopping(String item, int quantityToDelete) {
		if(toppings.containsKey(item)) {
			if(toppings.get(item) < quantityToDelete) {
				System.out.println("Error: Quantity to delete is greater than quantity in stock");
				// should we just make it 0?
			}
			else{
				toppings.put(item, toppings.get(item) - quantityToDelete);
			}
		}
	}
	
	public String toString() {
		String ret = name + ":";
		for(String k : toppings.keySet()) {
			ret += " " + toppings.get(k) + " " + k;
		}
		return ret;
	}
}
