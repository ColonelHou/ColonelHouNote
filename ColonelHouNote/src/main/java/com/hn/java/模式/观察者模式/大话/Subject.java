package com.hn.java.模式.观察者模式.大话;


import java.util.ArrayList;
import java.util.List;

public class Subject
{

	private List<Observer> observers = new ArrayList<Observer>();
	
	public void add(Observer o)
	{
		observers.add(o);
	}
	
	public void remove(Observer o)
	{
		observers.remove(o);
	}
	
	public void notifys()
	{
		for (Observer o : observers)
		{
			o.update();
		}
	}
}
