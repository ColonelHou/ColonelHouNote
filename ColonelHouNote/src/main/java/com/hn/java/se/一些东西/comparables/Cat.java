package com.hn.java.se.一些东西.comparables;

public class Cat implements Comparable<Cat>
{

	private int age;
	private String name;

	public Cat(int age, String name)
	{
		super();
		this.age = age;
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

	public String getName()
	{
		return name;
	}

	@Override
	public String toString()
	{
		return "Cat [age=" + age + ", name=" + name + "]";
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int compareTo(Cat o)
	{
		return this.getAge() - o.getAge();
	}

}
