package com.hn.java.模式.单例模式.v5;

public class SingletonKerriganE
{
	/**
	 * 单例对象实例
	 * 
	 * 饿汉式
	 * 
	 * 
	 * 问题：饿汉式的创建方式在一些场景中将无法使用：譬如Kerrigan实例的创建是依赖参数或者配置文件的，
	 * 在getInstance()之前必须调用某个方法设置参数给它，那样这种单例写法就无法使用了。
	 */
	private static SingletonKerriganE instance = new SingletonKerriganE();

	public static SingletonKerriganE getInstance()
	{
		return instance;
	}

}
