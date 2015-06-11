package com.hn.java.se.多线程;

public class Deadlocker
{

	private static Object lock_1 = new int[1];
	private static Object lock_2 = new int[1];

	public class Thread1 extends Thread
	{

		@Override
		public void run()
		{
			System.out.println("thread 1 start");
			synchronized (lock_1)
			{
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("thread 1 get lock 1 need lock 2");
				synchronized (lock_2)
				{
				}
			}
			System.out.println("thread 1 end");
		}
	}

	public class Thread2 extends Thread
	{

		@Override
		public void run()
		{
			System.out.println("thread 2 start");
			synchronized (lock_2)
			{
				try
				{
					Thread.sleep(5000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				System.out.println("thread 2 get lock 2 need lock 1");
				synchronized (lock_1)
				{
				}
			}
			System.out.println("thread 2 end");
		}
	}

	public static void main(String[] args)
	{
		Thread1 thread1 = new Deadlocker().new Thread1();
		Thread2 thread2 = new Deadlocker().new Thread2();
		thread1.start();
		thread2.start();
	}
}
