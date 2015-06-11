package com.hn.java.模式.代理模式.动态代理.jdk;

public class BookFacadeImpl implements BookFacade
{

	@Override
	public void addBook()
	{
		System.out.println("增加图书方法。。。");
	}

}
