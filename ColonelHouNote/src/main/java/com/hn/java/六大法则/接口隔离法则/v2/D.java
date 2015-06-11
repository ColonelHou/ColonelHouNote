package com.hn.java.六大法则.接口隔离法则.v2;

public class D implements I1, I3
{
	public void method1()
	{
		System.out.println("类D实现接口I1的方法1");
	}

	public void method4()
	{
		System.out.println("类D实现接口I3的方法4");
	}

	public void method5()
	{
		System.out.println("类D实现接口I3的方法5");
	}
}
