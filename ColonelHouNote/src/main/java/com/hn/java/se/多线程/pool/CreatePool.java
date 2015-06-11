package com.hn.java.se.多线程.pool;


import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Java线程：线程池
 * 
 * http://blog.csdn.net/coding_or_coded/article/details/6856014
 * 
 */
public class CreatePool {
	public static void main(String[] args) {
		notChangeThreadPool();
		/*couldChangeThreadPool();
		SingleThreadExecutor();
		relayThreadPool();*/
		
	}
	
	/**
	 * 改变ExecutorService pool = Executors.newFixedThreadPool(5)中的参数：
	 * pool-1-thread-1正在执行。。。
     * pool-1-thread-4正在执行。。。
     * pool-1-thread-2正在执行。。。
     * pool-1-thread-3正在执行。。。
     * pool-1-thread-5正在执行。。。
     * 
	 * ExecutorService pool = Executors.newFixedThreadPool(2)，输出结果是：
	 * pool-1-thread-1正在执行。。。
     * pool-1-thread-2正在执行。。。
     * pool-1-thread-1正在执行。。。
     * pool-1-thread-2正在执行。。。
     * pool-1-thread-1正在执行。。。
     * 
     * 从以上结果可以看出，newFixedThreadPool的参数指定了可以运行的线程的最大数目，超过这个数目的线程加进去以后，
     * 不会运行。其次，加入线程池的线程属于托管状态，线程的运行不受加入顺序的影响。
	 */
	public static void notChangeThreadPool()
	{
		// 创建一个可重用【固定】线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		// 创建线程  
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
	
	/**
	 * 【可变尺寸的线程池】
     * 这种方式的特点是：可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
	 */
	public static void couldChangeThreadPool()
	{
		ExecutorService pool = Executors.newCachedThreadPool();
		// 创建线程  
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
	
	/**
	 * 【单任务线程池】
	 * 可以看出，每次调用execute方法，其实最后都是调用了thread-1的run方法。
	 */
	public static void SingleThreadExecutor()
	{
		ExecutorService pool = Executors.newSingleThreadExecutor();
		// 创建线程  
		Thread t1 = new MyThread();
		Thread t2 = new MyThread();
		Thread t3 = new MyThread();
		Thread t4 = new MyThread();
		Thread t5 = new MyThread();
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
	
	/**
	 * 延迟连接池
	 */
	public static void relayThreadPool()
	{
		// 创建一个线程池，它可安排在给定延迟后运行命令或者定期地执行。  
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);  
        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口  
        Thread t1 = new MyThread();  
        Thread t2 = new MyThread();  
        Thread t3 = new MyThread();  
        // 将线程放入池中进行执行  
        pool.execute(t1);  
        // 使用延迟执行风格的方法  
        pool.schedule(t2, 1000, TimeUnit.MILLISECONDS);  
        pool.schedule(t3, 10, TimeUnit.MILLISECONDS);  
  
        // 关闭线程池  
        pool.shutdown(); 
	}
}

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
	}
}

