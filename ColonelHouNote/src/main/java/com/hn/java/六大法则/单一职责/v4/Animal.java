package com.hn.java.六大法则.单一职责.v4;

public class Animal
{

	public void breathe(String animal)
	{
		System.out.println(animal + "呼吸空气");
	}

	public void breathe2(String animal)
	{
		System.out.println(animal + "呼吸水");
	}
}
