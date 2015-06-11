package com.hn.java.模式.代理模式.静态代理.simple;

public class Proxy implements Subject
{

	Subject realSubject;

	public Proxy(Subject realSubject2)
	{
		this.realSubject = realSubject2;
	}

	@Override
	public void request()
	{

		if (null == realSubject)
		{
			realSubject = new RealSubject();
		}
		realSubject.request();
	}

}
