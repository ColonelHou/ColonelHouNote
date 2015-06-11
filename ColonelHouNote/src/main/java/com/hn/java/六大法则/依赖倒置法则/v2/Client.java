package com.hn.java.六大法则.依赖倒置法则.v2;

/**
 * 
 * @author Colonel.Hou
 * 
 *         这样修改无论以后怎样扩展Client类，都不需要修改Mother类了，
 * 
 */
public class Client
{
	public static void main(String[] args)
	{
		Mother mother = new Mother();
		mother.narrate(new Book());
		mother.narrate(new Newspaper());
	}
}
