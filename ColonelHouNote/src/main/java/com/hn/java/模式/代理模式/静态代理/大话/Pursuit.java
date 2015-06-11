package com.hn.java.模式.代理模式.静态代理.大话;

public class Pursuit implements GiveGift
{

	SchoolGirl mm;

	public Pursuit(SchoolGirl mm)
	{
		super();
		this.mm = mm;
	}

	@Override
	public void giveDolls()
	{
		System.out.println(mm.getName() + " 送你洋娃娃");
	}

	@Override
	public void giveFlowers()
	{
		System.out.println(mm.getName() + " 送你花");
	}

	@Override
	public void giveChocolate()
	{
		System.out.println(mm.getName() + "送你巧克力");
	}

}
