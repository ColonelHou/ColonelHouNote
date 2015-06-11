package com.hn.java.模式.代理模式.动态代理.myiteye;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SayHandler implements InvocationHandler
{

	private Object obj;

	public SayHandler(Object obj)
	{
		super();
		this.obj = obj;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable
	{
		System.out.println("预处理...");
		System.out.println("安全检查");
		System.out.println("权限检查等");
		Object result = method.invoke(this.obj, args);
		System.out.println("后续处理...");
		return result;
	}

}
