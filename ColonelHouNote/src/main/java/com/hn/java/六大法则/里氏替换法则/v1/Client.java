package com.hn.java.六大法则.里氏替换法则.v1;

/**
 * 
 * @author Colonel.Hou 要增加一个新的功能
 * 
 *         完成两数相加，然后再与100求和，由类B来负责。即类B需要完成两个功能
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		A a = new A();
		System.out.println("100-50=" + a.func1(100, 50));
		System.out.println("100-80=" + a.func1(100, 80));
	}
}
