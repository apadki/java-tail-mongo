package org.tests.jtail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class LogWriter {
	
	public static void main(String args[]){
		LogWriter logWriter = new LogWriter();
		Writer  writer = logWriter.new Writer("/logs/a.log");
		new Thread(writer).start();
		writer = logWriter.new Writer("/logs/b.log");
		new Thread(writer).start();
		
		
	}
	
	class Writer implements Runnable{
		
		private String fileName;
		
		Writer (String fileName){
			this.fileName = fileName;
		}

		public void run() {
			int i =0;
			while (true){
				
				File file = new File(fileName);
				try {
					RandomAccessFile localRandomAccessFile = new RandomAccessFile(file,
					        "rw");
					localRandomAccessFile.seek(localRandomAccessFile.length());
					localRandomAccessFile.writeBytes(i++ +  " New String  \n");
					Thread.sleep(3000);
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
