package com.hn.java.jdk7.autoClose;

import java.io.Closeable;

public class NewResource implements Closeable{
//public class NewResource implements AutoCloseable{

	String closingMsg;

	public NewResource(String closingMsg) {
		super();
		this.closingMsg = closingMsg;
	}
	
	public void doSomeWork(String work)throws ExceptionA
	{
		System.out.println(work);
		throw new ExceptionA("Exception occured while doing work");
	}
	
	//public void close()throws ExceptionB
	public void close()
	{
		System.out.println("Closing the resource");
		//throw new ExceptionB("Exception occured while doing close");
	}
	
	public void doSomeWork(NewResource res)throws ExceptionA
	{
		res.doSomeWork("Wow res getting res to do work	");
	}
}
