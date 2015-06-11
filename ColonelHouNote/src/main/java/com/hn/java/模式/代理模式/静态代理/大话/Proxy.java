package com.hn.java.模式.代理模式.静态代理.大话;

public class Proxy implements GiveGift
{

	Pursuit gg;

	public Proxy(SchoolGirl mm)
	{
		super();
		this.gg = new Pursuit(mm);
	}

	@Override
	public void giveDolls()
	{
		// TODO Auto-generated method stub
		gg.giveDolls();
	}

	@Override
	public void giveFlowers()
	{
		// TODO Auto-generated method stub
		gg.giveFlowers();
	}

	@Override
	public void giveChocolate()
	{
		// TODO Auto-generated method stub
		gg.giveChocolate();
	}

}
