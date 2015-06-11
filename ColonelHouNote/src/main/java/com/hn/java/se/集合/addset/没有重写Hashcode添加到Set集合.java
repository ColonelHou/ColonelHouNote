package com.hn.java.se.集合.addset;

import java.util.Set;

import java.util.HashSet;
import java.util.Hashtable;

/**
 * hashCode相等 一定是同一个对象。但是反之缺不一定。
 * @author 123
 *
 */
public class 没有重写Hashcode添加到Set集合
{

	public static void main(String[] args)
	{
		UserInfo i1 = new UserInfo("zz", 11);
		UserInfo i2 = new UserInfo("zz", 11);

		Set<UserInfo> s = new HashSet<UserInfo>();
		s.add(i1);
		s.add(i2);
		// 判断i1的内容是否和i2的相等
		System.out.println(i1.equals(i2));
		// i1的hashCode
		System.out.println(i1.hashCode());
		// i2的hashCode
		System.out.println(i2.hashCode());
		// S 集合的元素个数
		System.out.println(s.size());
		System.out.println(i1 == i2);
	}
}
