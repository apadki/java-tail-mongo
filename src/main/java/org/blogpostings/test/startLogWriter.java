package org.blogpostings.test;

import org.blogpostings.logwriter.LogWriter;

public class startLogWriter {
	public static void main(String[] args){
		LogWriter logWriter = new LogWriter();
		logWriter.start("spring-config.xml");
	}

}
