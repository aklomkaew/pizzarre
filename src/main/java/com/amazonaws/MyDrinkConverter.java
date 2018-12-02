package com.amazonaws;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents a DynamoDBTypeConverter for the Drink class
 * 
 * @author Atchima
 *
 */
public class MyDrinkConverter implements DynamoDBTypeConverter<String, List<Drink>> {

	/**
	 * Converts the specified list of Drink to string so it can be stored in
	 * DynamoDB
	 */
	@Override
	public String convert(List<Drink> object) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			String objStr = objMapper.writeValueAsString(object);
			return objStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Unconverts the specified string object to list of Drink so that the class can
	 * process the object
	 */
	@Override
	public List<Drink> unconvert(String objStr) {
		ObjectMapper objMapper = new ObjectMapper();
		try {
			List<Drink> obj = objMapper.readValue(objStr, new TypeReference<List<Drink>>() {
			});
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
