package com.hn.java.模式.代理模式.静态代理.v1;

/**
 * 
 * @author Colonel.Hou
 * 
 *         实际被代理对象：Tank
 * 
 */
public class Tank implements Moveable
{

	@Override
	public void move()
	{
		System.out.println("TanK moving........");
	}

}
