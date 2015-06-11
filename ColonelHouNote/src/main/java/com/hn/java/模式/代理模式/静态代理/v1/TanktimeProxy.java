package com.hn.java.模式.代理模式.静态代理.v1;

/**
 * 
 * @author Colonel.Hou
 * 
 *         代理主题角色：TanktimeProxy
 * 
 */
public class TanktimeProxy implements Moveable
{
	private Moveable t;

	public TanktimeProxy(Moveable t)
	{
		super();
		this.t = t;
	}

	@Override
	public void move()
	{
		long time1 = System.currentTimeMillis();
		System.out.println("time1=" + time1);
		t.move();
		long time2 = System.currentTimeMillis();
		System.out.println("time2=" + time2);
		System.out.println("运行时间为:" + (time2 - time1));
	}
}
