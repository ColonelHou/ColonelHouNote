package com.hn.java.se.genData;

public class Awk
{

	public static void main(String[] args)
	{
		String separator = "\t";
		for (int i = 65; i <= 90; i++)
		{
			System.out.println(((char) i) + "." + ((char) (i + 32)) + "dust"
					+ separator + "0" + (i - 64) + "/99" + separator + "78923"
					+ separator + ((char) i) + "tom" + separator + (i - 23)
					+ separator + (i - 45) + separator + (i - 56));
		}
	}
}
