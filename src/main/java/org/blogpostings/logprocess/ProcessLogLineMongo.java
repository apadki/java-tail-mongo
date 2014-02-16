package org.blogpostings.logprocess;

import java.net.UnknownHostException;

public class ProcessLogLineMongo implements ProcessLogLine {
	
	 

	public void processLine(String line) {
		// TODO Auto-generated method stub
		/**
		 * INFO:10.0.9.2:Meena:2014 02 15 19:12:31.342:Session timed out:1391
		 * 
		 */
		try {
			LogEntryDAO logEntryDAO =MongoStart.getMongoStart().getLongEntryDAO();
			
			
			logEntryDAO.insert(line);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// insert(line);
		 
	}
	 

	

}
