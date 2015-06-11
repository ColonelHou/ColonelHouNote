package com.hn.java.se.注解.iteye;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * http://globalzhu.iteye.com/blog/619403 类注解
 * 
 * @author leizhimin 2009-12-18 14:15:46
 */

@Retention(RetentionPolicy.RUNTIME)
// 注解针对的目标（类、方法、字段）
@Target(ElementType.TYPE)
public @interface MyAnnotation4Class {
	public String msg();
}
