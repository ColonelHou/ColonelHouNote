package com.hn.java.模式.代理模式.动态代理.cglib;

public class TestCglib
{

	public static void main(String[] args)
	{
		BookFacadeCglib cglib = new BookFacadeCglib();
		BookFacadeImpl bookCglib = (BookFacadeImpl) cglib
				.getInstance(new BookFacadeImpl());
		bookCglib.addBook();
	}
}
