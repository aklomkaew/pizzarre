package com.amazonaws;

import java.util.*;

public class Pizza {
	private String name;
	private ArrayList<String> toppings;
	private int size;	// S = 1, M = 2, L = 3
	
	public Pizza() {
		this.name = "";
		this.size = 0;
		this.toppings = new ArrayList<String>();
	}
	
	public Pizza(String n, int s, ArrayList<String> list) {
		this.name = n;
		this.size = s;
		this.toppings = new ArrayList<String>();
		this.toppings.addAll(list);
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
	
	public ArrayList<String> getToppings(){
		return this.toppings;
	}
	
	public String toString() {
		String ret = "";
		
		for(String str : this.toppings) {
			ret += str + ", ";
		}
		ret = ret.substring(0, ret.length() - 2);
		return ret;
	}
}
