package com.hn.opensource.junit;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * http://www.cnblogs.com/gpcuster/archive/2009/10/03/1577716.html
 * 
 * @author hadoop
 * 
 */
public class MyTestJUnit
{

	@Test
	public void test()
	{
		int result = 1;
		int expected = 1;
		assertEquals(result, expected);
	}

}
