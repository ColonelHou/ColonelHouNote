package com.hn.java.六大法则.单一职责.v3;

/**
 * 
 * @author Colonel.Hou 这样做花销小了，但是违反了单一职责
 * 
 * 
 *         问题：存在隐患， 有一天发现将鱼呼吸分为淡水鱼与海水鱼
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
		animal.breathe("鱼");
	}
}
