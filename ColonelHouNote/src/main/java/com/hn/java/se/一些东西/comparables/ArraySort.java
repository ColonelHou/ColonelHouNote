package com.hn.java.se.一些东西.comparables;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ArraySort
{

	public static void main(String[] args)
	{
		String[] strArray = new String[] { "z", "a", "C" };
		System.out.println("-------------数组转换为列表-------------");
		List<String> list = Arrays.asList(strArray);
		outCollection(list);

		System.out.println("-------------顺序排序列表-------------");
		Collections.sort(list);
		outCollection(list);

		System.out
				.println("-----按String实现的Comparator对象String.CASE_INSENSITIVE_ORDER排序----");
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		outCollection(list);

		System.out.println("-------------倒序排序列表-------------");
		Collections.sort(list, Collections.reverseOrder());
		outCollection(list);

		System.out
				.println("-----按String实现的Comparator对象String.CASE_INSENSITIVE_ORDER排序----");
		Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		outCollection(list);

		System.out.println("-----反转列表元素的顺序------");
		Collections.reverse(list);
		outCollection(list);
	}

	public static String outCollection(Collection coll)
	{
		StringBuffer sb = new StringBuffer();
		for (Object obj : coll)
		{
			sb.append(obj + "\t");
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
