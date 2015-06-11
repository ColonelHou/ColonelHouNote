package com.hn.java.se.一些东西.comparables;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Test
{

	public static void main(String[] args)
	{
		System.out.println("升序排序测试:");
		List<Cat> listCat = new ArrayList<Cat>();
		Cat cat1 = new Cat(34, "hehe");
		Cat cat2 = new Cat(12, "haha");
		Cat cat3 = new Cat(23, "leizhimin");
		Cat cat4 = new Cat(13, "lavasoft");

		listCat.add(cat1);
		listCat.add(cat2);
		listCat.add(cat3);

		System.out.println("原集合为:");
		outCollection(listCat);

		System.out.println("调用Collections.sort(List<T> list)排序：");
		Collections.sort(listCat);
		outCollection(listCat);

		System.out.println("逆序排列元素:");
		Collections.sort(listCat, Collections.reverseOrder());
		outCollection(listCat);

		System.out.println("再次逆序排列元素:");
		Collections.reverse(listCat);
		outCollection(listCat);

	}

	public static String outCollection(Collection coll)
	{
		StringBuffer sb = new StringBuffer();
		for (Object obj : coll)
		{
			sb.append(obj + "/n");
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
}
