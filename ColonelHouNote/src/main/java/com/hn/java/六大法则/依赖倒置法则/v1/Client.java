package com.hn.java.六大法则.依赖倒置法则.v1;

/**
 * 
 * @author Colonel.Hou
 * 
 *         需求变更：不是给妈妈书，而是给了一份报纸来讲故事
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		Mother mother = new Mother();
		mother.narrate(new Book());
	}
}
