package com.hn.java.模式.观察者模式.大话;

public class Client
{

	public static void main(String[] args)
	{
		ConcreteSubject subject = new ConcreteSubject();

		subject.add(new ConcreteObserver("X", subject));
		subject.add(new ConcreteObserver("Y", subject));
		subject.add(new ConcreteObserver("Z", subject));

		subject.setSubjectState("ABC");
		subject.notifys();
	}
}
