package com.hn.java.模式.代理模式.动态代理.动态代理发生了什么;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

/**
 * @author yeyong
 */
public class ProxyTest2
{

	public static void main(String[] args) throws Exception
	{

		// 添加以下的几段代码, 就可以将代理生成的字节码保存起来了
		Field field = System.class.getDeclaredField("props");
		field.setAccessible(true);
		Properties props = (Properties) field.get(null);
		props.put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

		Package pkg = ProxyTest2.class.getPackage();
		if (pkg != null)
		{
			String packagePath = pkg.getName().replace(".", File.pathSeparator);
			new File(packagePath).mkdirs();
		}

		IA a = new IAImpl();
		InvocationHandlerImpl ih = new InvocationHandlerImpl(a);
		IA proxyA = (IA) Proxy.newProxyInstance(a.getClass().getClassLoader(),
				a.getClass().getInterfaces(), ih);

		proxyA.a();   
	}
}

interface IA
{
	void a();

	int b(String str);
}

class IAImpl implements IA
{

	@Override
	public void a()
	{
		System.out.println("IAImpl.a()");
	}

	@Override
	public int b(String str)
	{
		System.out.println("IAImpl.b()");
		return 0;
	}
}

class InvocationHandlerImpl implements InvocationHandler
{

	private Object target;

	public InvocationHandlerImpl(Object target)
	{
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable
	{
		System.out.println("before...");
		Object res = method.invoke(target, args);
		System.out.println("after...");
		return res;
	}
}
