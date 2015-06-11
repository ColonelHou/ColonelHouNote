package com.hn.java.模式.策略模式.大话.v1;

public class Client
{

	public static void main(String[] args)
	{
		Context context = new Context(new ConcreteAStrategy());
		context.setStrategy(new ConcreateBStrategy());
		context.contextInterface();

	}
}
