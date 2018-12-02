package com.amazonaws;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;

/**
 * Represents a DynamoDBTypeConverter for boolean variables
 * 
 * @author Atchima Reference:
 *         https://stackoverflow.com/questions/45384293/dynamodb-dynamodb-mapper-returns-null-for-boolean-value
 */
public class MyBooleanConverter implements DynamoDBTypeConverter<String, Boolean> {

	/**
	 * Converts the specified boolean object to string so it can be stored in
	 * DynamoDB
	 */
	@Override
	public String convert(Boolean object) {
		try {
			return object.toString();
		} catch (Exception e) {
			System.out.println("Cannot convert boolean");
		}
		return null;
	}

	/**
	 * Unconverts the specified string object to boolean so that the class can
	 * process the variable
	 */
	@Override
	public Boolean unconvert(String object) {
		try {
			return Boolean.parseBoolean(object);
		} catch (Exception e) {
			System.out.println("Cannot unconvert boolean");
		}
		return null;
	}

}
