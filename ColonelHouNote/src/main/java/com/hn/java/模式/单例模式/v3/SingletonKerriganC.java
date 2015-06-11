package com.hn.java.模式.单例模式.v3;

public class SingletonKerriganC
{
	/**
	 * 单例对象实例
	 */
	private static SingletonKerriganC instance = null;

	/**
	 * 把同步方法改为同步块， 减少性能损耗
	 * 
	 * 问题：每次调用此方法的时候还是需要同步的， 但是我们希望在第一次创建的时候进行同步
	 * 
	 * @return
	 */
	public static synchronized SingletonKerriganC getInstance()
	{
		synchronized (SingletonKerriganC.class)
		{
			if (instance == null)
			{ // line A
				instance = new SingletonKerriganC(); // line B
			}
		}

		return instance;
	}

}
