package com.hn.java.模式.代理模式.静态代理.simple;

public class Client
{

	public static void main(String[] args)
	{
		Subject realSubject = new RealSubject();
		Proxy proxy = new Proxy(realSubject);
		proxy.request();
	}
}
