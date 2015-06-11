package com.hn.java.模式.单例模式.v2;

public class SingletonKerriganB
{
	/**
	 * 单例对象实例
	 */
	private static SingletonKerriganB instance = null;

	/**
	 * 跟版本一相比就是多了一个同步， 这样不会出现多线程问题了， 问题：这样很耗性能的， 线程同步很耗性能的
	 * 
	 * @return
	 */
	public static synchronized SingletonKerriganB getInstance()
	{
		if (instance == null)
		{ // line A
			instance = new SingletonKerriganB(); // line B
		}
		return instance;
	}

}
