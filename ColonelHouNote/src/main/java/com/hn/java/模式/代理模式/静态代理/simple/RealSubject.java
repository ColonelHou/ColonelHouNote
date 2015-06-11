package com.hn.java.模式.代理模式.静态代理.simple;

public class RealSubject implements Subject
{

	@Override
	public void request()
	{
		System.out.println("真实的请求");
	}

}
