package org.blogpostings.logprocess;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class LogEntryDAO {
	
	DBCollection logEntryCollection ;
	
	public LogEntryDAO(final DB logDB){
		this.logEntryCollection = logDB.getCollection("logEntry");
	}
	/**
	 * INFO:10.0.9.2:Meena:2014 02 15 19:12:31.342:Session timed out:1391
	 * 
	 */
	public void insert(String eventLog) {
		
		String[] tokens = eventLog.split(";");
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy MM dd HH:mm:ss.SSS");
		DateTime dateTime = formatter.parseDateTime(tokens[4]);
		java.util.Date date = new Date(dateTime.getMillis());
		System.out.println(tokens);
		logEntryCollection.insert(new BasicDBObject("logFile",  tokens[0].trim()).append
				("logLevel", tokens[1].trim()).append("host", tokens[2].trim()).append("user", tokens[3].trim())
				.append("message",  tokens[5].trim()).append("responsemillis",  tokens[6].trim()).append("reportedat", date).
				append("page", tokens[7]));
	}

	 
}
