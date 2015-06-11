package com.hn.java.六大法则.接口隔离法则.v2;

public class C
{
	public void depend1(I1 i)
	{
		i.method1();
	}

	public void depend2(I3 i)
	{
		i.method4();
	}

	public void depend3(I3 i)
	{
		i.method5();
	}
}
