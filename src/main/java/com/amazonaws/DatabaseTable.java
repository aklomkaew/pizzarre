package com.amazonaws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

/**
 * Represents the base class of all database table
 * @author Atchima
 *
 */
public abstract class DatabaseTable {

	protected static AmazonDynamoDB client;
	protected static DynamoDBMapper mapper;
	protected static DynamoDB dynamoDb;

	/**
	 * Class constructor
	 * @throws Exception
	 */
	public DatabaseTable() throws Exception {
		initDb();
		dynamoDb = new DynamoDB(client);
		mapper = new DynamoDBMapper(client);
	}

	/**
	 * Initializes database by assigning client the credentialsProvider
	 * of AWS by reading from the credentials file located at
	 * (Users/[username]/.aws/credentials).
	 * @throws Exception
	 */
	private void initDb() throws Exception {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/Users/[username]/.aws/credentials), and is in valid format.", e);
		}
		client = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider).withRegion("us-east-2")
				.build();
	}
	
	/**
	 * Creates a new database table with name tableName
	 * If the table already exists, then prints out the message stating so
	 * @param tableName A string representing table name
	 */
	protected void createNewTable(String tableName) {
		if (dynamoDb.getTable(tableName) != null) {
			System.out.println(tableName + " already exists.");
		} else {
			CreateTableRequest req = mapper.generateCreateTableRequest(InventoryItem.class);
			req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
			dynamoDb.createTable(req);
		}
	}
}
