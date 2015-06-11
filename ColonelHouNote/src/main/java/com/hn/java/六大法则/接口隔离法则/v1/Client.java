package com.hn.java.六大法则.接口隔离法则.v1;

/**
 * 
 * @author Colonel.Hou 可以看到接口过于臃肿， 只要接口中出现的方法，不管对依赖于它的类有没有用处， 实现类都需要去实现这些方法，
 *         这显然是不好的设计
 * 
 *         解决方案：将臃肿的接口拆分为几个独立的接口
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		A a = new A();
		a.depend1(new B());
		a.depend2(new B());
		a.depend3(new B());

		C c = new C();
		c.depend1(new D());
		c.depend2(new D());
		c.depend3(new D());
	}
}
