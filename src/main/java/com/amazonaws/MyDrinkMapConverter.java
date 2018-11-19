package com.amazonaws;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyDrinkMapConverter implements DynamoDBTypeConverter<String, HashMap<Drink, Integer>> {

	@Override
	public String convert(HashMap<Drink, Integer> object) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			String objStr = objMapper.writeValueAsString(object);
			return objStr;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HashMap<Drink, Integer> unconvert(String objStr) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			HashMap<Drink, Integer> obj = objMapper.readValue(objStr, new TypeReference<HashMap<Drink, Integer>>(){});
			return obj;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
