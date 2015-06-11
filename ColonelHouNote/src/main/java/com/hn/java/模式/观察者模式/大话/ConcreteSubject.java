package com.hn.java.模式.观察者模式.大话;

public class ConcreteSubject extends Subject
{

	private String subjectState;

	public String getSubjectState()
	{
		return subjectState;
	}

	public void setSubjectState(String subjectState)
	{
		this.subjectState = subjectState;
	}

}
