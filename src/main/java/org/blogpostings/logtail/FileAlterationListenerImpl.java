package org.blogpostings.logtail;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * @author rm87341
 *
 */
public class FileAlterationListenerImpl implements FileAlterationListener { //FileAlterationListenerAdaptor //FileAlterationListener
    private static final Log LOG = LogFactory.getLog(FileAlterationListenerImpl.class);
   
    BlockingQueue<String> fileNameQ;
    LogTailConfig logMonitorConfig;
    String dtftStr;
    ConcurrentHashMap<String, Long> activeFiles;
   
     public FileAlterationListenerImpl(BlockingQueue<String> fileNameQ, LogTailConfig logMonitorConfig, 
             ConcurrentHashMap<String, Long> activeFiles){
         this.fileNameQ = fileNameQ;
         this.logMonitorConfig = logMonitorConfig;
         this.dtftStr = dtftStr;
         this.activeFiles = activeFiles;
         LOG.info("FileAlterationListenerImpl Constructor");
         
     }
    /**
     * {@inheritDoc}
     */
  
    public void onStart(final FileAlterationObserver observer) {
        //LOG.debug("The WindowsFileListener has started on " + observer.getDirectory().getAbsolutePath());
    }
 
    /**
     * {@inheritDoc}
     */
   
    public void onDirectoryCreate(final File directory) {
        //LOG.debug(directory.getAbsolutePath() + " was created.");
    }
 
    /**
     * {@inheritDoc}
     */
 
    public void onDirectoryChange(final File directory) {
        //LOG.debug(directory.getAbsolutePath() + " wa modified");
    }
 
    /**
     * {@inheritDoc}
     */
    
    public void onDirectoryDelete(final File directory) {
        //LOG.debug(directory.getAbsolutePath() + " was deleted.");
    }
 
    /**
     * {@inheritDoc}
     */
 // Is triggered when a file is created in the monitored folder
             String strfileName;

             
             public void onFileCreate(File file) {
                 try {
                     // "file" is the reference to the newly created file

                     System.out.println("File created: "
                             + file.getCanonicalPath());
                     strfileName = file.getAbsolutePath();
                     if ((strfileName.contains("INCOMING") || strfileName
                             .contains("OUTGOING")))

                     {
                activeFiles.put(file.getCanonicalPath(), 0L);
                         fileNameQ.add(file.getCanonicalPath());
                     }
                 } catch (IOException e) {
                     e.printStackTrace(System.err);
                 }
             }

             
             public void onFileChange(File file) {
                 try {
                     // "file" is the reference to the newly created file
                     System.out.println("File changed: "
                             + file.getCanonicalPath());
                     strfileName = file.getAbsolutePath();
                     
                         fileNameQ.add(file.getCanonicalPath());
                     

                 } catch (IOException e) {
                     e.printStackTrace(System.err);
                 }
             }

             // Is triggered when a file is deleted from the monitored folder
             
             public void onFileDelete(File file) {
                 try {
                     // "file" is the reference to the removed file
                     System.out.println("File deleted: "
                             + file.getCanonicalPath());
                     strfileName = file.getAbsolutePath();
                     if ((strfileName.contains("INCOMING") || strfileName
                             .contains("OUTGOING")))

                     {
                         activeFiles.remove(file.getCanonicalPath());
                     }
                     // "file" does not exists anymore in the location
                     System.out.println("File still exists in location: "
                             + file.exists());
                 } catch (IOException e) {
                     e.printStackTrace(System.err);
                 }
             }
 
    /**
     * {@inheritDoc}
     */
    
    public void onStop(final FileAlterationObserver observer) {
        //LOG.debug("The WindowsFileListener has stopped on " + observer.getDirectory().getAbsolutePath());
    }

}
