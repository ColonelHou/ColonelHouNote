package com.hn.java.模式.代理模式.静态代理.大话;

public class Client
{

	public static void main(String[] args)
	{
		SchoolGirl jiaojiao = new SchoolGirl();
		jiaojiao.setName("丽丽");

		Proxy daili = new Proxy(jiaojiao);

		daili.giveChocolate();
		daili.giveDolls();
		daili.giveFlowers();
	}

}
