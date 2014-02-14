package org.tests.jtail;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import org.apache.log4j.Logger;

public class LogLineConsumer implements Runnable {

	BlockingQueue<String> linequeue = null;
	CountDownLatch latch;
	private static final Logger log = Logger.getLogger(LogLineConsumer.class
			.getName());

	public LogLineConsumer(BlockingQueue<String> linequeue, CountDownLatch latch) {
		this.linequeue = linequeue;

		this.latch = latch;
	}

	public void run() {

		try {

			while (true) {

				String logLine = linequeue.take();
				log.info(logLine);

			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

		}

	}

}
