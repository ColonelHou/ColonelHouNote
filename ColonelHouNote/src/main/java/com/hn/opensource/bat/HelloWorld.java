package com.hn.opensource.bat;

/**
 * @echo off
 * 
 *       set home=lib\bat.jar;
 * 
 *       start /wait batUserJre\jre\bin\javaw -classpath %home% -Xms32m -Xmx512m
 *       -Duser.region=CN -Duser.language=zh com.test.Test
 * @author hadoop
 * 
 */
public class HelloWorld
{

	public static void main(String[] args)
	{
		System.out.println("Hello World! Bat");
	}
}
