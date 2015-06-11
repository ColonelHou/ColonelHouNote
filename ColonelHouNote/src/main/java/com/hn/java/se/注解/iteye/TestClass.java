package com.hn.java.se.注解.iteye;

/**
 * 写一个类，用上这些注解 一个普通的Java类
 * 
 * @author hadoop
 * 
 */
@MyAnnotation4Class(msg = "测试类注解信息")
public class TestClass
{
	@MyAnnotation4Field(commont = "成员变量的注解信息", request = true)
	private String testfield;

	@MyAnnotation4Method(msg1 = "测试方法注解信息1", msg2 = "测试方法注解信息2")
	public void testMethod()
	{
		System.out.println("Hello World！");
	}
}
