package com.hn.java.se.注解.iteye;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段注解
 * 
 * @author leizhimin 2009-12-18 15:23:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyAnnotation4Field {
	public String commont();

	public boolean request();
}
