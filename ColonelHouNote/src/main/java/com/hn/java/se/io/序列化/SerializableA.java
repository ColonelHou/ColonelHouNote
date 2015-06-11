package com.hn.java.se.io.序列化;

import java.io.Serializable;

public class SerializableA extends SerializableB implements Serializable
{

	public SerializableA(int b)
	{
		super(b);
	}

	private static final long serialVersionUID = 1L;
	public int a = 1;
}
