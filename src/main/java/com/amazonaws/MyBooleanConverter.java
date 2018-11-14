package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

public class MyBooleanConverter implements DynamoDBTypeConverter<String, Boolean> {

	@Override
	public String convert(Boolean object) {
		try {
			return object.toString();
		} catch(Exception e) {
			System.out.println("Cannot convert boolean");
		}
		return null;
	}

	@Override
	public Boolean unconvert(String object) {
		try {
			return Boolean.parseBoolean(object);
		} catch(Exception e) {
			System.out.println("Cannot unconvert boolean");
		}
		return null;
	}

}
