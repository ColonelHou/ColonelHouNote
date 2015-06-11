package com.hn.java.六大法则.依赖倒置法则.v1;

public class Mother
{
	public void narrate(Book book)
	{
		System.out.println("妈妈开始讲故事");
		System.out.println(book.getContent());
	}
}
