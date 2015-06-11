package com.hn.java.se.collection.list;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * remove(int index)都是对新的数组进行修改和新增。
 * 所以在多线程操作时不会出现java.util.ConcurrentModificationException错误。
 * 最后得出结论：CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存。
 * 发生修改时候做copy，新老版本分离，保证读的高性能，适用于以读为主的情况
 * @author 123
 *
 */
public class ConcurrentModificationException解决CopyOnWriteArrayList
{
	/**
	 * 读线程
	 * 
	 * @author wangjie
	 *
	 */
	private static class ReadTask implements Runnable
	{
		List<String> list;

		public ReadTask(List<String> list)
		{
			this.list = list;
		}

		public void run()
		{
			for (String str : list)
			{
				System.out.println(str);
			}
		}
	}

	/**
	 * 写线程
	 * 
	 * @author wangjie
	 *
	 */
	private static class WriteTask implements Runnable
	{
		List<String> list;
		int index;

		public WriteTask(List<String> list, int index)
		{
			this.list = list;
			this.index = index;
		}

		public void run()
		{
			list.remove(index);
			list.add(index, "write_" + index);
		}
	}

	public void run()
	{
		final int NUM = 10;
		//List<String> list = new ArrayList<String>();
		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<String>();
		for (int i = 0; i < NUM; i++)
		{
			list.add("main_" + i);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(NUM);
		for (int i = 0; i < NUM; i++)
		{
			executorService.execute(new ReadTask(list));
			executorService.execute(new WriteTask(list, i));
		}
		executorService.shutdown();
	}

	public static void main(String[] args)
	{
		new ConcurrentModificationException解决CopyOnWriteArrayList().run();
	}
}
