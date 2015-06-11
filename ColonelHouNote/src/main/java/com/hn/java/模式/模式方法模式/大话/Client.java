package com.hn.java.模式.模式方法模式.大话;

public class Client
{

	public static void main(String[] args)
	{
		AbstractClass c;

		c = new ConcreteClassA();
		c.templateMethod();

		c = new ConcreteClassB();
		c.templateMethod();
	}
}
