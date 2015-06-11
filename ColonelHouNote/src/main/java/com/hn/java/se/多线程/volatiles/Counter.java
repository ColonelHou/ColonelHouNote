package com.hn.java.se.多线程.volatiles;

/**
 * 
 * http://m.blog.csdn.net/blog/u014223536/37761867
 * @author Colonel.Hou
 * 分析一下原因:
 * 在 java 垃圾回收整理一文中，描述了jvm运行时刻内存的分配。其中有一个内存区域是jvm虚拟机栈，
 * 每一个线程运行时都有一个线程栈，线程栈保存了线程运行时候变量值信息。当线程访问某一个对象时候值的时候，
 * 首先通过对象的引用找到对应在堆内存的变量的值，然后把堆内存变量的具体值load到线程本地内存中，
 * 建立一个变量副本，之后线程就不再和对象在堆内存变量值有任何关系，而是直接修改副本变量的值，
 * 在修改完之后的某一个时刻（线程退出之前），自动把线程变量副本的值回写到对象在堆中变量。
 * 这样在堆中的对象的值就产生变化了。
 * 
 * 
 * 对于volatile修饰的变量，jvm虚拟机只是保证从主内存加载到线程工作内存的值是最新的
 * 例如假如线程1，线程2 在进行read,load 操作中，发现主内存中count的值都是5，那么都会加载这个最新的值
 * 在线程1堆count进行修改之后，会write到主内存中，主内存中的count变量就会变为6
 * 线程2由于已经进行read,load操作，在进行运算之后，也会更新主内存count的变量值为6
 * 导致两个线程及时用volatile关键字修改之后，还是会存在并发的情况。
 *
 */
public class Counter
{

	public volatile static int count = 0;

	public static void inc()
	{

		// 这里延迟1毫秒，使得结果明显
		try
		{
			Thread.sleep(1);
		} catch (InterruptedException e)
		{
		}

		count++;
	}

	public static void main(String[] args)
	{

		// 同时启动1000个线程，去进行i++计算，看看实际结果

		for (int i = 0; i < 1000; i++)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					Counter.inc();
				}
			}).start();
		}

		// 这里每次运行的值都有可能不同,可能为1000
		//运行结果:Counter.count=985
		System.out.println("运行结果:Counter.count=" + Counter.count);
	}
}
