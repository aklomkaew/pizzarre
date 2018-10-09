package com.amazonaws;

import java.util.*;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;


public abstract class DatabaseTable {
	
	private AmazonDynamoDB client;
	
	public DatabaseTable() {
		client = null;
	}
	
	public void initDb() throws Exception {
        /*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (/Users/Atchima/.aws/credentials).
         */
        ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
        try {
            credentialsProvider.getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the credentials from the credential profiles file. " +
                    "Please make sure that your credentials file is at the correct " +
                    "location (/Users/Atchima/.aws/credentials), and is in valid format.",
                    e);
        }
        client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(credentialsProvider)
            .withRegion("us-east-2")
            .build();
    }
	
	public AmazonDynamoDB getClient() throws Exception {
		if(client == null) {
			initDb();
		}
		return client;
	}
	
	public void listAllTables() {
		DynamoDB dynamoDB =  new DynamoDB(client);
		TableCollection<ListTablesResult> tables = dynamoDB.listTables();
		Iterator<Table> iterator = tables.iterator();
		
		System.out.println("All tables: " );
		while(iterator.hasNext()) {
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
