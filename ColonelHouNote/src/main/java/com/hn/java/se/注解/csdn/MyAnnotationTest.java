package com.hn.java.se.注解.csdn;

import java.lang.reflect.Method;

/**
 * 注解测试
 * 
 * @author Fly
 */
@AnnotationTest(sex = "男", name = "张飞")
public class MyAnnotationTest
{

	@AnnotationTest(sex = "男", name = "Fly")
	public void setFly()
	{
	}

	@AnnotationTest(sex = "女", name = "李明")
	public void setLiMing()
	{
	}

	public static void main(String[] args)
	{
		// 检查类MyAnnotationTest是否含有@AnnotationTest注解
		if (MyAnnotationTest.class.isAnnotationPresent(AnnotationTest.class))
		{
			// 若存在就获取注解
			AnnotationTest annotation = (AnnotationTest) MyAnnotationTest.class
					.getAnnotation(AnnotationTest.class);
			System.out.println(annotation);
			// 获取注解属性
			System.out.println(annotation.sex());
			System.out.println(annotation.name());
			System.out.println("///////////////////////////////////////////");
			Method[] _methods = MyAnnotationTest.class.getDeclaredMethods();
			for (Method method : _methods)
			{
				System.out.println(method);
				if (method.isAnnotationPresent(AnnotationTest.class))
				{
					AnnotationTest test = method
							.getAnnotation(AnnotationTest.class);
					System.out.println("AnnotationTest(method="
							+ method.getName() + ",name=" + test.name()
							+ ",sex=" + test.sex() + ")");

				}
			}
		}
	}
}
