package com.hn.java.se.collection.map;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class 解决Map集合不可重复IdentityHashMap
{

	public static void main(String[] args)
	{
		Map<Person, String> map = new HashMap<Person, String>(); 
		map.put(new Person("jim", 24),  "jim -- >  10");
		map.put(new Person("tom", 13),  "tom -- >  10");
		//这条数据会把上面的jim覆盖掉, 因为Person对象认为是相同的
		map.put(new Person("jim", 24),  "jim -- >  20");
		System.out.println(map.toString());
		
		
		Map<Person, String> identityMap = new IdentityHashMap<Person, String>(); 
		identityMap.put(new Person("jim", 24),  "jim -- >  10");
		identityMap.put(new Person("tom", 13),  "tom -- >  10");
		//没有把上面的jim覆盖掉, 因为Person对象引用不是相同的
		identityMap.put(new Person("jim", 24),  "jim -- >  20");
		System.out.println(identityMap.toString());
		
		Map<Person, String> linkMap = new LinkedHashMap<Person, String>(); 
		linkMap.put(new Person("jim", 24),  "jim -- >  10");
		linkMap.put(new Person("tom", 13),  "tom -- >  10");
		//覆盖了
		linkMap.put(new Person("jim", 24),  "jim -- >  20");
		System.out.println(linkMap.toString());
	}
}

class Person
{
	private String name;
	private int age;
	public Person(String name, int age)
	{
		super();
		this.name = name;
		this.age = age;
	}
	public Person()
	{
		super();
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getAge()
	{
		return age;
	}
	public void setAge(int age)
	{
		this.age = age;
	}
	@Override
	public String toString()
	{
		return "Person [name=" + name + ", age=" + age + "]";
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (age != other.age)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
