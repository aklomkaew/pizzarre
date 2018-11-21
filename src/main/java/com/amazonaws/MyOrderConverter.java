package com.amazonaws;

import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyOrderConverter implements DynamoDBTypeConverter<String, List<Order>> {

	@Override
	public String convert(List<Order> object) {
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
	public List<Order> unconvert(String objStr) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			List<Order> obj = objMapper.readValue(objStr, new TypeReference<List<Order>>(){});
			return obj;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
