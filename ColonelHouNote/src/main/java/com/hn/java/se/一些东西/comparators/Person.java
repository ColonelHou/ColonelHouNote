package com.hn.java.se.一些东西.comparators;

public class Person
{

	private int age;

	private String name;

	public Person(int age, String name)
	{
		super();
		this.age = age;
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Person [age=" + age + ", name=" + name + "]";
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

	public void setName(String name)
	{
		this.name = name;
	}

}
