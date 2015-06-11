package com.hn.java.六大法则.单一职责.v1;

public class Client
{
	public static void main(String[] args)
	{
		Animal animal = new Animal();
		animal.breathe("牛");
		animal.breathe("羊");
		animal.breathe("猪");
	}
}
