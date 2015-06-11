package com.hn.opensource.lucene.Three_Five;

import org.apache.lucene.index.IndexReader;

/**
 * 注意构造方法中每次都是初始化,
 * 
 * @author hadoop
 * 
 */
public class TestIndex
{

	public static void main(String[] args)
	{
		testSearch02();
	}

	public static void testIndex()
	{
		IndexUtil iu = new IndexUtil();
		iu.index();
	}

	public static void testQuery()
	{
		IndexUtil iu = new IndexUtil();
		iu.delete();
		iu.query();
	}

	public static void testDelete()
	{
		IndexUtil iu = new IndexUtil();
		iu.delete();
	}

	public static void testDelete02()
	{
		IndexUtil iu = new IndexUtil();
		iu.delete02();
	}

	public static void testUnDelete()
	{
		IndexUtil iu = new IndexUtil();
		iu.undelete();
	}

	public static void testForceDelete()
	{
		IndexUtil iu = new IndexUtil();
		iu.forceDelete();
	}

	public static void testMerge()
	{
		IndexUtil iu = new IndexUtil();
		iu.merge();
	}

	public static void testUpdate()
	{
		IndexUtil iu = new IndexUtil();
		iu.update();
	}

	public static void testSearch01()
	{
		IndexUtil iu = new IndexUtil();
		iu.search01();
	}

	public static void testSearch02()
	{

		IndexUtil iu = new IndexUtil();
		// 执行五次都没问题, 因为reader没有关闭;
		/**
		 * 在执行查询的时候进行删除, 发现删除没有起作用 如果reader发生变化了,就重新打开一个reader
		 * IndexReader.openIfChanged(reader);
		 */
		for (int i = 0; i < 5; i++)
		{
			iu.search02();
			System.out.println("-----------------------------");
			try
			{
				Thread.sleep(10000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
