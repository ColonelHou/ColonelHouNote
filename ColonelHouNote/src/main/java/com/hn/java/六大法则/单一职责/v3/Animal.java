package com.hn.java.六大法则.单一职责.v3;

public class Animal
{

	// 违反了方法级别的单一职责
	public void breathe(String animal)
	{
		if ("鱼".equals(animal))
		{
			System.out.println(animal + "呼吸水");
		} else
		{
			System.out.println(animal + "呼吸空气");
		}
	}
}
