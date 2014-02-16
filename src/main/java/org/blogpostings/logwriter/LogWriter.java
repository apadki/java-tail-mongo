package org.blogpostings.logwriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.blogpostings.logprocess.ProcessLogLine;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LogWriter {
	
	private LogWriterConfig logWriterConfig;
	
	private List<String> fileNames;
	
	private Random generator = new Random();
	
	private static DateTimeFormatter LONG_FORMATTER = DateTimeFormat.forPattern("yyyy MM dd HH:mm:ss.SSS");

	
	private List<String> logLevel = Arrays.asList("DEBUG", "INFO", "SEVERE");
			
	private List<String> logMessages = Arrays.asList("Password does not match",
			  "Prices Did not exit",
			  "Product not found ",
			  "Inventory not found",
			  "Unsupported Operation",
			  "Price not as expected",
			  "Cannot Access Data",
			  "Session timed out",
			  "Data Very Large");
	
	private List<String> hosts = Arrays.asList("10.0.9.1", "10.0.9.2", "10.9.8.1");
	
	private List<String> users = Arrays.asList("Larry", "Drew", "Nachiket", "Sunil", "Jacob", "Vladimar", "Mami", "Meena",
			"Hema", "Thor", "Dominique");
	
	private List<String> requests = Arrays.asList("http://get/products",
			"http://get/produt?id=10",
			"http://get/inventory/product?id=20",
			"http://get/price/product?id=20",
			"http://validateUser");
	
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	
	
	public List<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<String> fileNames) {
		this.fileNames = fileNames;
	}

	public void start(String appContextFileName){
		
	    ApplicationContext  context = new ClassPathXmlApplicationContext(appContextFileName); 
	    LogWriterConfig logWriterConfig = (LogWriterConfig)
	    		context.getBean("logWriterConfig");
		//LogWriter logWriter = new LogWriter(logWriterConfig);
		for (String fileName : logWriterConfig.getLogfiles()){
			new Thread(new Writer(fileName, logWriterConfig.getWriteInterval())).start();
		}
		
	}
	
	public void init(){		
		
	}
	
	class Writer implements Runnable{
		
		
		private String fileName;
		long writerInterval;
		
		
		
		Writer (String fileName, long writerInterval){
			this.fileName = fileName;
			this.writerInterval = writerInterval;
		}

		public void run() {
			int i =0;
			while (true){
				
				File file = new File(fileName);
				try {
					RandomAccessFile localRandomAccessFile = new RandomAccessFile(file,
					        "rw");
					localRandomAccessFile.seek(localRandomAccessFile.length());
					String logMessage= null;
					int index = generator.nextInt(2);
					int index1 = generator.nextInt(8);
					int index2 = generator.nextInt(4);
					int range = 1000;
					int responseTime =  generator.nextInt(range) + 1000;
					
					DateTime dt = new DateTime();
					
					String strDate = sdfDate.format(new Date());
					logMessage = logLevel.get(index) + ";" + hosts.get(index) +";" + 
						users.get(index1) +";" + dt.toString(LONG_FORMATTER) +";" + logMessages.get(index1) +";" + responseTime 
						 + ";"  +requests.get(index2) +"\n" ;
					localRandomAccessFile.writeBytes(logMessage );
					Thread.sleep(writerInterval);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
						// TODO Auto-generated method stub
			
		}
		
		
	}
	

}
