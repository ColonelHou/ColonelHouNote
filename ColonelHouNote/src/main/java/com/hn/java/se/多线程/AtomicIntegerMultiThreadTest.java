package com.hn.java.se.多线程;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;




/*
 * http://www.itzhai.com/the-introduction-and-use-of-atomicinteger.html#read-more
 * 与网上说的测试结果不一致,不清楚原因
 * 
 * 
Elapsed: 1921ms, value=10000000 
Elapsed: 353ms, value=10000000 （AtomicInteger的性能是synchronized的5倍多） 

当给value加上volatile修饰符时： 

Elapsed: 2268ms, value=10000000 （volatile禁止代码重排序，一定程度上降低了性能） 
Elapsed: 337ms, value=10000000 

当调用未同步的自增方法unSyncIncrease时： 

Elapsed: 216ms, value=5852266 （非原子操作不加同步，导致结果错误） 
Elapsed: 349ms, value=10000000
 */
public class AtomicIntegerMultiThreadTest {
	private/* volatile */int value;

	public AtomicIntegerMultiThreadTest(int value) {
		this.value = value;
	}

	public synchronized int increase() {
		return value++;
	}

	public int unSyncIncrease() {
		return value++;
	}

	public int get() {
		return value;
	}

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();

		final CountDownLatch latch = new CountDownLatch(100);

		final AtomicIntegerMultiThreadTest test = new AtomicIntegerMultiThreadTest(
				0);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 100000; i++) {
						test.increase();
						// test.unSyncIncrease();
					}

					latch.countDown();
				}
			}).start();
		}

		latch.await();
		long end = System.currentTimeMillis();
		System.out.println("Elapsed: " + (end - start) + "ms, value="
				+ test.get());

		long start2 = System.currentTimeMillis();
		final CountDownLatch latch2 = new CountDownLatch(100);
		final AtomicInteger atomicInt = new AtomicInteger(0);
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < 100000; i++) {
						atomicInt.incrementAndGet();
					}

					latch2.countDown();
				}
			}).start();
		}

		latch2.await();
		long end2 = System.currentTimeMillis();
		System.out.println("Elapsed: " + (end2 - start2) + "ms, value="
				+ atomicInt.get());
	}
}
