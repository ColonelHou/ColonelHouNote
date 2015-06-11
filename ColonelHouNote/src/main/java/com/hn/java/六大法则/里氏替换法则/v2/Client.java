package com.hn.java.六大法则.里氏替换法则.v2;

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
		B b = new B();
		System.out.println("100-50=" + b.func1(100, 50));
		// 相减功能发生了错误
		// 100-80=180
		System.out.println("100-80=" + b.func1(100, 80));
		System.out.println("100+20+100=" + b.func2(100, 20));
	}
}
