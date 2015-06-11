package com.hn.java.六大法则.单一职责.v4;

/**
 * 
 * @author Colonel.Hou 式没有改动原来的方法，而是在类中新加了一个方法，这样虽然也违背了单一职责原则，
 *         但在方法级别上却是符合单一职责原则的
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		Animal animal = new Animal();
		animal.breathe("牛");
		animal.breathe("羊");
		animal.breathe("猪");
		animal.breathe2("鱼");
	}
}
