package com.amazonaws;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;

public class UserDb extends DatabaseTable {
	private AmazonDynamoDB client;

	private DynamoDB dynamoDB;

	private String tableName;

	private DynamoDBMapper mapper;

	public UserDb() throws Exception {
		tableName = "my-users";
		dynamoDB = null;
		client = getClient();

		mapper = createNewTable();
		initTable(mapper);
		retrieveAllItem(mapper);
	}

	public String getTableName() {
		return tableName;
	}

	public DynamoDBMapper createNewTable() throws InterruptedException {
		dynamoDB = new DynamoDB(client);
		DynamoDBMapper mapper = new DynamoDBMapper(client);

		if (dynamoDB.getTable(tableName) != null) {
			System.out.println(tableName + " already exists.");
		} else {
			mapper = new DynamoDBMapper(client);
			CreateTableRequest req = mapper.generateCreateTableRequest(User.class);
			req.setProvisionedThroughput(new ProvisionedThroughput(5L, 5L));
			dynamoDB.createTable(req);
		}

		return mapper;
	}

	public void initTable(DynamoDBMapper mapper) {
		System.out.println("\nInitializing table " + tableName);

		if (mapper == null) {
			System.out.println("mapper is null");
			return;
		}

		User u = new User();
		u.setName("John Doe");
		u.setUserId(10);

		mapper.save(u);

		u.setName("Bob Marley");
		u.setUserId(11);
		mapper.save(u);

		u.setName("Mary Allen");
		u.setUserId(12);
		mapper.save(u);
	}

	public void retrieveAllItem(DynamoDBMapper mapper) {
		List<User> itemList = mapper.scan(User.class, new DynamoDBScanExpression());
		// DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>();

		System.out.println("\nRetrieving all users");
		for (User item : itemList) {
			System.out.println("Id = " + item.getUserId() + " name = " + item.getName());
		}
	}
}
