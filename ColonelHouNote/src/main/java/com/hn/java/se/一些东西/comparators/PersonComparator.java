package com.hn.java.se.一些东西.comparators;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person>
{

	public int compare(Person p1, Person p2)
	{
		// TODO Auto-generated method stub
		return p1.getAge() - p2.getAge();
	}
}
