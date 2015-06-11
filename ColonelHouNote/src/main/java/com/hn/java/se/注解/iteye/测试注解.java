package com.hn.java.se.注解.iteye;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

//当然，注解也可以没有定义成员，这样注解就成了一个标记符号了。 
public class 测试注解
{
	public static void main(String[] args) throws NoSuchMethodException,
			NoSuchFieldException
	{
		TestClass t = new TestClass();
		System.out.println("-----------MyAnnotation4Class注解信息---------");
		// 必须通过Java的反射技术来获取Annotation对象，因为你除此之外没有别的获取注解对象的方法
		MyAnnotation4Class an4clazz = t.getClass().getAnnotation(
				MyAnnotation4Class.class);
		// 获取了注解对象，就可以调用注解的方法来获取相对应的值了。为基础框架所用。
		System.out.println(an4clazz.msg());

		System.out.println("-----------MyAnnotation4Method注解信息---------");
		Method method = t.getClass().getMethod("testMethod", new Class[0]);
		MyAnnotation4Method an4method = method
				.getAnnotation(MyAnnotation4Method.class);
		System.out.println(an4method.msg1());
		System.out.println(an4method.msg2());

		System.out.println("-----------MyAnnotation4Field注解信息---------");
		Field field = t.getClass().getDeclaredField("testfield");
		MyAnnotation4Field an4field = field
				.getAnnotation(MyAnnotation4Field.class);
		System.out.println(an4field.commont());
		System.out.println(an4field.request());
	}
}
