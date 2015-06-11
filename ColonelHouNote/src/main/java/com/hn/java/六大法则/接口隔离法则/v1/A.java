package com.hn.java.六大法则.接口隔离法则.v1;

public class A
{
	public void depend1(I i)
	{
		i.method1();
	}

	public void depend2(I i)
	{
		i.method2();
	}

	public void depend3(I i)
	{
		i.method3();
	}
}
