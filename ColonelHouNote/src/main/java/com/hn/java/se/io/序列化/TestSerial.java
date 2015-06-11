package com.hn.java.se.io.序列化;

import java.io.Externalizable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerial
{
	public static void main(String[] args) throws IOException,
			ClassNotFoundException
	{
		
		SerializableA a = new SerializableA(100);
		String file = "E:/b.out";
		FileOutputStream out = new FileOutputStream(file);
		ObjectOutputStream objOut = new ObjectOutputStream(out);
		System.out.println(a.b);
		System.out.println(a.a);
		objOut.writeObject(a);
		objOut.flush();
		objOut.close();

		//
		/**
		 * 原因:由于父类不可序列化，故序列化没有问题，但是反序列化出问题了。
		 * 解决:首先SerializableB  不可序列化。其次反序列化时，
		 *     由于SerializableB  没有提供一个默认的无参构造器，故报错。
		 *     你只需要加一个无参构造器构就是正确的。
		 *     子类会负责调用父类的无参构造器来恢复不可序列化的类的状态。
		 * 分析:在此情况下，子类负责保存和恢复不可序列化的类的状态; 
		 *     因为在反序列化的过程中并不会使用声明为可序列化的类A的任何构造函数，
		 *     而是会调用其没有申明为可序列化 的父类B的无参构造函数。 
		 */
		
		/**
		 * java.io.InvalidClassException: 
		 * com.hn.java.se.io.序列化.SerializableA; 
		 * no valid constructor
		 */
		FileInputStream in = new FileInputStream("E:/b.out");
		ObjectInputStream objIn = new ObjectInputStream(in);
		SerializableA b = (SerializableA)objIn.readObject();
		System.out.println(b.b) ;
		System.out.println(b.a) ;

		
	}
}
