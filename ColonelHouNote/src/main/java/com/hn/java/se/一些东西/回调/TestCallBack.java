package com.hn.java.se.一些东西.回调;

public class TestCallBack
{
	public void doSomething(IEventCallBack callBack)
	{
		System.out.println("Something is done!");
		callBack.call("Call back method is called!");
	}

	public static void main(String[] args)
	{
		TestCallBack call = new TestCallBack();
		// 使用有回调的方法时，传入接口，并临时实现这个回调方法
		call.doSomething(new IEventCallBack()
		{
			@Override
			public void call(String sayWhat)
			{
				System.out.println(sayWhat);
			}
		});
	}
}
