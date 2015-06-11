package com.hn.java.模式.代理模式.静态代理.v1;

/**
 * 
 * @author Colonel.Hou
 * 
 *         实际主题传入到代理类中， 在调用实际主题的处理逻辑前后做一些处理
 * 
 */
public class TestTank
{
	public static void main(String[] args)
	{
		Tank t = new Tank();
		Moveable move = new TanktimeProxy(t);
		move.move();

	}
}
