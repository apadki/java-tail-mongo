package org.blogpostings.logtail;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class LogFilesTail{
   
   ApplicationContext context = null;

   public static ConcurrentHashMap<String, Long> activeFiles = new ConcurrentHashMap<String, Long>();

   private static final Logger log = Logger.getLogger(LogFilesTail.class
           .getName());

     public void start(String appContextFileName) {

       long start = System.currentTimeMillis();

       ApplicationContext  context = new ClassPathXmlApplicationContext(appContextFileName); 

       CountDownLatch latch = new CountDownLatch(3);

       LogTailConfig logMonitorConfig = (LogTailConfig) context
               .getBean("logMonitorConfig");   
     
       File folder = new File(logMonitorConfig.getLogDir());
       if (!folder.exists()) {
           // Test to see if monitored folder exists
           log.info("  Directory not found Cannot Proceed : "
                   + logMonitorConfig.getLogDir());
           throw new RuntimeException("Directory not found: "
                   + logMonitorConfig.getLogDir());
       }

       log.debug("logdir :" + logMonitorConfig.getLogDir());   

       final BlockingQueue<String> fileNameQ = new LinkedBlockingQueue<String>(
               1024);
       final BlockingQueue<String> logLineQ = new LinkedBlockingQueue<String>(
               1024);
       
       FileNameConsumer fileNameConsumer = new FileNameConsumer(fileNameQ,
               logLineQ, activeFiles, latch);

       new Thread(fileNameConsumer, "File Name Consumer").start();

       LogLineConsumer logLineConsumer = new LogLineConsumer(logLineQ,
               logMonitorConfig.getProcessLogLine(), latch);

       new Thread(logLineConsumer, "Log Line Consumer").start();

       String pattern ="\\S*log$";
      
       String fileNamePattern = getFileNamePattern(pattern);

       File[] files = folder.listFiles();
      log.info("FILE PATTERN TO MATCH " + fileNamePattern);
       for (File file : files) {
           try {

               String strfileName = file.getName();

               if (matchedFileName(strfileName, fileNamePattern.toString()))

               {
                   log.info("adding filename to FileQueue " + strfileName);

                   activeFiles.put(file.getCanonicalPath(), 0L);

                   fileNameQ.add(file.getCanonicalPath());
               }
           } catch (IOException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
           }

       }

       log.info(" Putting EOF on fileNameq ");
           FileAlterationObserver observer = new FileAlterationObserver(folder);
         FileAlterationMonitor monitor = new FileAlterationMonitor(
        logMonitorConfig.getPollingInterval());
       
        FileAlterationListener listener = new FileAlterationListenerImpl(
        fileNameQ, logMonitorConfig, activeFiles);
        observer.addListener(listener); monitor.addObserver(observer); try {
       monitor.start(); } 
        
        catch (Exception e) { // TODO Auto-generated catch  block 
    	   e.printStackTrace(); }
   
       try {
           latch.await();
       } catch (InterruptedException e1) {
           // TODO Auto-generated catch block
           e1.printStackTrace();
       }
       long end = System.currentTimeMillis();

       log.info(" ** END  TIME " + end);

       log.info(" ALL THREADS DONE : PROCESSING OVER");

       log.info(" ** TOTAL TIME TAKEN in Seconds " + (end - start) / 1000);

       
    }

   private String getFileNamePattern(String pattern) {

       StringBuffer fileNamePattern = new StringBuffer();
       fileNamePattern.append(pattern);
       return fileNamePattern.toString();

   }

   private boolean matchedFileName(String fileName, String fileNamePattern) {
       return fileName.matches(fileNamePattern);

   }
  
   
}
