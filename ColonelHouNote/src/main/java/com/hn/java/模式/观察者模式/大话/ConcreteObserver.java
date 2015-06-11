package com.hn.java.模式.观察者模式.大话;

public class ConcreteObserver extends Observer
{

	private String name;
	private String observerState;
	private ConcreteSubject subject;

	public ConcreteObserver(String name, ConcreteSubject subject)
	{
		super();
		this.name = name;
		this.subject = subject;
	}

	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		observerState = subject.getSubjectState();
		System.out.println(name + "  --> " + observerState);
	}

}
