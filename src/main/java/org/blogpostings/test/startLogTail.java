package org.blogpostings.test;

import java.net.UnknownHostException;

import org.blogpostings.logprocess.MongoStart;
import org.blogpostings.logtail.LogFilesTail;

public class startLogTail {
	
	public static void main(String[] args){
		LogFilesTail logFilesTail = new LogFilesTail();
		logFilesTail.start("spring-config.xml");
		
	}

}
