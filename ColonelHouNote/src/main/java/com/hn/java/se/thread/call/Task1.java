package com.hn.java.se.thread.call;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.Callable;

public class Task1 implements Callable<String> {

	@Override
	public String call() throws Exception {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		System.out.println("Task1 running: " + new Date());
		return sb.toString();
	}

}
