package com.hn.java.se.注解.iteye;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 方法注解
 * 
 * @author leizhimin 2009-12-18 14:16:05
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyAnnotation4Method {
	public String msg1();

	public String msg2();
}
