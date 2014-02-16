package org.blogpostings.logprocess;

import java.net.UnknownHostException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoStart {
	private static String mongoURI;
	private static String dbName;
	public void setDbName(String dbName) {
		MongoStart.dbName = dbName;
	}

	private static LogEntryDAO logEntryDAO ;
	
	public void setMongoURI(String mongoURI) {
		mongoURI = mongoURI;
	}

	private static MongoStart mongoStart =null;

	public static synchronized MongoStart getMongoStart() throws UnknownHostException{
		if (mongoStart == null) {
			MongoClient mongoClient = new MongoClient(mongoURI);
			DB db = mongoClient.getDB(dbName);
			logEntryDAO = new LogEntryDAO(db);
			mongoStart = new MongoStart();
			
		}
		return mongoStart;
	}
	
	
	
	public LogEntryDAO getLongEntryDAO(){
		return logEntryDAO;
	}

}
