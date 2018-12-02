package com.amazonaws;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a DynamoDBTypeConverter for the Pizza class
 * @author Atchima
 *
 */
public class MyPizzaConverter implements DynamoDBTypeConverter<String, List<Pizza>> {

	/**
	 * Converts the specified list of Pizza to string so it can be stored in DynamoDB
	 */
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

	/**
	 * Unconverts the specified string object to list of Pizza so that the class can process the object
	 */
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
