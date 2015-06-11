package com.hn.java.模式.模式方法模式.大话;

public abstract class AbstractClass
{

	public abstract void primitiveOperation1();

	public abstract void primitiveOperation2();

	public void templateMethod()
	{
		primitiveOperation1();
		primitiveOperation2();
	}
}
