package com.hn.java.六大法则.接口隔离法则.v2;

public class B implements I1, I2
{

	public void method1()
	{
		System.out.println("类B实现接口I1的方法1");
	}

	public void method2()
	{
		System.out.println("类B实现接口I2的方法2");
	}

	public void method3()
	{
		System.out.println("类B实现接口I2的方法3");
	}

}
