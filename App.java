package db;

import java.util.Iterator;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;


/**
 * Hello DynamoDB
 *
 */
public class App {
	static String ACCESS_KEY = "YOUR_ACCESS_KEY";
	static String SECRET_KEY = "YOUR_SECRET_KEY";
	
	public static void main(String[] args) {
		BasicAWSCredentials credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
//		EnvironmentVariableCredentialsProvider credentialsProvider =  new EnvironmentVariableCredentialsProvider();
		AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).withRegion(Regions.AP_SOUTHEAST_1).build();
		DynamoDB dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("Music");  

		QuerySpec spec = new QuerySpec().withKeyConditionExpression("Artist = :v_artist")
				.withValueMap(new ValueMap().withString(":v_artist", "A"));

		ItemCollection<QueryOutcome> items = table.query(spec);

		Iterator<Item> iterator = items.iterator();
		Item item = null;
		while (iterator.hasNext()) {
			item = iterator.next();
			System.out.println(item.toJSONPretty());
		}
		
    // Put item into table
		table.putItem(new Item().withPrimaryKey("Artist","E").withString("SongTitle", "F").withString("Band", "G"));
	}
}

