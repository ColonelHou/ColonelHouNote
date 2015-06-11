package com.hn.java.模式.代理模式.动态代理.myiteye;

public class ServerSayHelloimple implements SayHello
{

	@Override
	public void sayHello(String name)
	{
		System.out.println(name + " say : Hello World!!");
	}

}
