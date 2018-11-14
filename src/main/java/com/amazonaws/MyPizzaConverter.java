package com.amazonaws;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyPizzaConverter implements DynamoDBTypeConverter<String, List<Pizza>> {

	@Override
	public String convert(List<Pizza> object) {
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
	public List<Pizza> unconvert(String objStr) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			List<Pizza> obj = objMapper.readValue(objStr, new TypeReference<List<Pizza>>(){});
			return obj;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
