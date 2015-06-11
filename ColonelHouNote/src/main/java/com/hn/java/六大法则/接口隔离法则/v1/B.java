package com.hn.java.六大法则.接口隔离法则.v1;

public class B implements I
{
	public void method1()
	{
		System.out.println("类B实现接口I的方法1");
	}

	public void method2()
	{
		System.out.println("类B实现接口I的方法2");
	}

	public void method3()
	{
		System.out.println("类B实现接口I的方法3");
	}

	// 对于类B来说，method4和method5不是必需的，但是由于接口A中有这两个方法，
	// 所以在实现过程中即使这两个方法的方法体为空，也要将这两个没有作用的方法进行实现。
	public void method4()
	{
	}

	public void method5()
	{
	}
}
