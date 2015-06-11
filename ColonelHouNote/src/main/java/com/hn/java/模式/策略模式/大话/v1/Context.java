package com.hn.java.模式.策略模式.大话.v1;

public class Context
{

	Strategy strategy;

	public Strategy getStrategy()
	{
		return strategy;
	}

	public Context(Strategy strategy)
	{
		super();
		this.strategy = strategy;
	}

	public void setStrategy(Strategy strategy)
	{
		this.strategy = strategy;
	}

	public void contextInterface()
	{
		strategy.algorithmInterface();
	}
}
