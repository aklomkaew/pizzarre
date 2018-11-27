package com.amazonaws;

import java.util.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;

public abstract class DatabaseTable {

	protected static AmazonDynamoDB client;
	protected static DynamoDBMapper mapper;
	protected static DynamoDB dynamoDb;

	public DatabaseTable() throws Exception {
		initDb();
		dynamoDb = new DynamoDB(client);
		mapper = new DynamoDBMapper(client);
		//mapper = new DynamoDBMapper(client, new DynamoDBMapperConfig(ConversionSchemas.V2));
	}

	public void initDb() throws Exception {
		/*
		 * The ProfileCredentialsProvider will return your [default] credential profile
		 * by reading from the credentials file located at
		 * (/Users/Atchima/.aws/credentials).
		 */
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/Atchima/.aws/credentials), and is in valid format.", e);
		}
		client = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider).withRegion("us-east-2")
				.build();
	}
	
	protected void createNewTable(String tableName) {
		if (dynamoDb.getTable(tableName) != null) {
			System.out.println(tableName + " already exists.");
		} else {
			CreateTableRequest req = mapper.generateCreateTableRequest(InventoryItem.class);
			req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
			dynamoDb.createTable(req);
		}
	}

//	public static AmazonDynamoDB getClient() throws Exception {
//		if (client == null) {
//			initDb();
//		}
//		return client;
//	}
//
//	public static DynamoDB getDynamoDb() throws Exception {
//		if(dynamoDb == null) {
//			if(client == null) {
//				initDb();
//			}
//			dynamoDb = new DynamoDB(client);
//		}
//		return dynamoDb;
//	}
//
//	public static DynamoDBMapper getMapper() throws Exception {
//		if (mapper == null) {
//			if (client == null) {
//				initDb();
//			}
//			mapper = new DynamoDBMapper(client);
//		}
//		return mapper;
//	}

	public void listAllTables() {
		DynamoDB dynamoDB = new DynamoDB(client);
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();

		System.out.println("All tables: ");
		while (iterator.hasNext()) {
			Table table = iterator.next();
			System.out.println(table.getTableName());
		}
	}

	public void printTable(String name) {
		System.out.println("Printing items in table: " + name);
		ScanRequest scanRequest = new ScanRequest().withTableName(name);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			System.out.println(item);
		}
	}

	public void deleteTable(String name) throws InterruptedException {
		DynamoDB dynamoDB = new DynamoDB(client);
		Table delTable = dynamoDB.getTable(name);

		try {
			delTable.delete();
			delTable.waitForDelete();
			System.out.println("Successfully deleted " + name);
		} catch (Exception e) {
			System.err.println("Cannot delete " + name);
			System.err.println(e.getMessage());
		}

		System.out.println("\nUpdated list: ");
		listAllTables();
	}
}
