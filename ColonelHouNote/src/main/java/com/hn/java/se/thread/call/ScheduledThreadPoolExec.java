package com.hn.java.se.thread.call;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExec {
	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(
				2);

		ScheduledFuture future1 = executor.schedule(new Task1(), 5,
				TimeUnit.SECONDS);
		ScheduledFuture future2 = executor.schedule(new LongTask(), 3,
				TimeUnit.SECONDS);

		/*BlockingQueue<ScheduledFuture> blockingQueue = new ArrayBlockingQueue<ScheduledFuture>(
				2, true);
		blockingQueue.add(future2);
		blockingQueue.add(future1);

		System.out.println(new Date());
		while (!blockingQueue.isEmpty()) {
			ScheduledFuture future = blockingQueue.poll();
			if (!future.isDone())
				blockingQueue.add(future);
			else
				System.out.println(future.get());
		}
		System.out.println(new Date());*/
		executor.shutdown();
	}
}
