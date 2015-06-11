package com.hn.java.模式.单例模式.v1;

public class SingletonKerriganA
{
	/**
	 * 单例对象实例
	 */
	private static SingletonKerriganA instance = null;

	/**
	 * 问题：多线程并发调用此方法，线程一判断完instance为NULL后，JVM将CPU资源切换给线程二，
	 * 由于线程一还没有执行以创建对象上，线程二判断也是空进行后创建对象， 切换到线程一又创建了一个对象， 这时谁是谁了，不知道了，问题了
	 * 
	 * @return
	 */
	public static SingletonKerriganA getInstance()
	{
		if (instance == null)
		{ // line A
			instance = new SingletonKerriganA(); // line B
		}
		return instance;
	}

}
