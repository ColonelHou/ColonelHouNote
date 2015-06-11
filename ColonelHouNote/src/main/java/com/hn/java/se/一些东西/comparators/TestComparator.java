package com.hn.java.se.一些东西.comparators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestComparator
{

	public static void main(String args[])
	{

		System.out.println("升序排序测试:");
		List<Person> listPerson = new ArrayList<Person>();
		Person person1 = new Person(34, "lavasoft");
		Person person2 = new Person(12, "lavasoft");
		Person person3 = new Person(23, "leizhimin");
		Person person4 = new Person(13, "sdg");

		listPerson.add(person1);
		listPerson.add(person2);
		listPerson.add(person3);
		listPerson.add(person4);

		Comparator<Person> ascComparator = new PersonComparator();

		System.out.println("原集合为:");
		outCollection(listPerson);

		System.out.println("排序后集合为:");
		Collections.sort(listPerson, ascComparator);
		outCollection(listPerson);

		System.out.println("/n降序排序测试:");
		// 从升序排序对象产生一个反转(降序)的排序对象
		Comparator<Person> descComparator = Collections
				.reverseOrder(ascComparator);
		System.out.println("利用反转后的排序接口对象对集合List排序并输出:");
		Collections.sort(listPerson, descComparator);
		outCollection(listPerson);

		System.out.println("/n求最大最小元素测试:");
		Person p_max = Collections.max(listPerson, ascComparator);
		Person p_min = Collections.min(listPerson, ascComparator);
		System.out.println("最大元素为:" + p_max.toString());
		System.out.println("最小元素为:" + p_min.toString());
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
