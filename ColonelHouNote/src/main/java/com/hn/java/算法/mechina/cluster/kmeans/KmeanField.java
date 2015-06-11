package com.hn.java.算法.mechina.cluster.kmeans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在对象的属性上标注此注释， 表示纳入kmeans算法,仅支持数值类属性
 * 
 * @author 阿飞哥
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KmeanField
{
}
