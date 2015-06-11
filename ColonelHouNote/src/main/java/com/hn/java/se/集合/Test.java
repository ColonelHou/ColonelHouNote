package com.hn.java.se.集合;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;

public class Test
{

	private String name;
	private int age;
	
	
	public static void main(String[] args)
	{
		String s1 = "abc";
		String s2 = "abc";
		String s3 = "abc";
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		System.out.println(s3.hashCode());
		System.out.println(1 % 1000);
		System.out.println(1001 % 1000);
		for (int i = 10000; i <= 100000; i++)
		{
			int t = i % 1000;
			//把\t改为空格就打印不出来了
			System.out.print(t + "\t");
		}
		
	}
}
