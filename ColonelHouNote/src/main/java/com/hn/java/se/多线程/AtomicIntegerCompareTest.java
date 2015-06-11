package com.hn.java.se.多线程;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * i++和++i都不是原子操作，多线程环境下需要使用synchronized关键字。
 * @author John
 *
 */
public class AtomicIntegerCompareTest {
	private int value;

	public AtomicIntegerCompareTest(int value) {
		this.value = value;
	}

	public synchronized int increase() {
		return value++;
	}

	public static void main(String args[]) {
		long start = System.currentTimeMillis();

		AtomicIntegerCompareTest test = new AtomicIntegerCompareTest(0);
		for (int i = 0; i < 10000000; i++) {
			test.increase();
		}
		long end = System.currentTimeMillis();
		System.out.println("time elapse1:" + (end - start));

		long start1 = System.currentTimeMillis();

		AtomicInteger atomic = new AtomicInteger(0);

		for (int i = 0; i < 10000000; i++) {
			atomic.incrementAndGet();
		}
		long end1 = System.currentTimeMillis(); 
		System.out.println("time elapse2:" + (end1 - start1));

	}
}
