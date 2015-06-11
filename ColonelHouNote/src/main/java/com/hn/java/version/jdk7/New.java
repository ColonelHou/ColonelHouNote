package com.hn.java.version.jdk7;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class New
{

	public static void main(String[] args)
	{
		Path path = Paths.get("/media/000BEE3B00094A8C/OpenSource/eclipseWksp/MavenPro/src/main/java/com/hn/java/version/jdk7/a.txt");
		//a.txt
		System.out.println(path.getFileName().toString());
		
		// /
		System.out.println(path.getRoot().toString());
		
		String str = "abc";
		switch (str)
		{
		case "abd":
			System.out.println("abd");
			break;
		case "abc":
			System.out.println("abc");
			break;
		default:
			break;
		}
		long creditCardNumber = 1234_5678_9012_3456L;
		int x2 = 5_2;
		System.out.println(x2);
	}
}
