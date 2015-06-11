package com.hn.java.六大法则.里氏替换法则.v2;

public class B extends A
{
	public int func1(int a, int b)
	{
		return a + b;
	}

	public int func2(int a, int b)
	{
		return func1(a, b) + 100;
	}
}
