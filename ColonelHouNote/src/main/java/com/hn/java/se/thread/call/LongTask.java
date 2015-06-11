package com.hn.java.se.thread.call;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class LongTask implements Callable<String> {

	@Override
	public String call() throws Exception {
		System.out.println("LongTask running: " + new Date());
		TimeUnit.SECONDS.sleep(10);
		return "success";
	}

}
