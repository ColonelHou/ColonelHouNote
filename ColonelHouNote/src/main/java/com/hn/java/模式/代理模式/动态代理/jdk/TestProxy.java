package com.hn.java.模式.代理模式.动态代理.jdk;

public class TestProxy
{
	public static void main(String[] args)
	{
		BookFacadeProxy proxy = new BookFacadeProxy();
		BookFacade bookProxy = (BookFacade) proxy.bind(new BookFacadeImpl());
		bookProxy.addBook();
	}
}
