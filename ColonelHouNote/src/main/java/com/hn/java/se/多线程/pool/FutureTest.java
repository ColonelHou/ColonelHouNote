package com.hn.java.se.多线程.pool;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

	public static class Task implements Callable<String> {

		private int i ;
		public Task(int i) {
			super();
			this.i = i;
		}

		@Override
		public String call() throws Exception {
			System.out.println("execute!!!");
			return "complete " + i;
		}
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		List<Future<String>> results = new ArrayList<Future<String>>();
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			results.add(executorService.submit(new Task(i)));
		}
		for (Future<String> future : results) {
			System.out.println(future.get());
		}

		System.out.println("Main complete");

		if (!executorService.isShutdown()) {
			executorService.shutdown();
		}
	}
}