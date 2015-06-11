package com.hn.java.六大法则.依赖倒置法则.v2;

/**
 * 代表高层模块的Mother类将负责完成主要的业务逻辑
 * 
 * @author Colonel.Hou
 * 
 */
public class Mother
{
	public void narrate(IReader reader)
	{
		System.out.println("妈妈开始讲故事");
		System.out.println(reader.getContent());
	}
}
