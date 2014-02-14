package org.tests.jtail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;





public class FileNameConsumer  implements Runnable{

BlockingQueue<String> lineQueue = null;
BlockingQueue<String> fileQueue = null;
ConcurrentHashMap<String, Long> activeFiles = new ConcurrentHashMap<String, Long>();
CountDownLatch latch= null;
private static final Logger log = Logger.getLogger(FileNameConsumer.class
        .getName());

public FileNameConsumer(BlockingQueue<String> fileQueue,
        BlockingQueue<String> lineQueue, ConcurrentHashMap<String, Long> activeFiles, CountDownLatch latch) {
    this.fileQueue = fileQueue;
    this.lineQueue = lineQueue;
    this.activeFiles = activeFiles;
    this.latch = latch;
}




public void run() {

    while (true) {
        try {

            String fileName = fileQueue.take();
           
            if (fileName.equalsIgnoreCase("EOF")) {
               // lineQueue.add(new LogLine(fileName, "") );
                log.info(" FILENAMEQ RECEIVED EOF ");
                latch.countDown();
                break;
            }
            File file = new File(fileName);

            long l = file.length();
            long filePointer = activeFiles.get(fileName);
            log.debug("FileName QUE GOT:  " + fileName +" : length " + l);
            RandomAccessFile localRandomAccessFile = null;
            if (l > filePointer) {

                try {
                    localRandomAccessFile = new RandomAccessFile(file,
                            "r");
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                localRandomAccessFile.seek(filePointer);
                String str = null;
                while ((str = localRandomAccessFile.readLine()) != null) {
                   
                    lineQueue.add(fileName + " : "  + str) ;
                }
               
                filePointer = localRandomAccessFile.getFilePointer();
                activeFiles.put(fileName, filePointer);
                localRandomAccessFile.close();
            }
           
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}

}