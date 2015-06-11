package com.hn.cluster.hadoop.mrs.统计最高气温;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

public class GenData
{

	public static void main(String[] args) throws Exception
	{
		/*
		 * String line = "004099999900001997NTEM+4+99999+00008"; String year =
		 * line.substring(14, 18); System.out.println(year); String temp =
		 * line.substring(23, 24); System.out.println(temp);
		 */

		// String s = "\"3\",\"user3\",\"0\"";
		String s = "\"1\",\"order1\"";
		String[] st = s.toString().split(",", 2);
		System.out.println(Arrays.toString(st));
	}

	public void genTemData() throws IOException
	{
		File f = new File("/opt/hadoop/test.log");
		if (!f.exists())
		{
			f.createNewFile();
		}
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream(f, true));
		Random r = new Random();
		for (int i = 0; i < 10; i++)
		{
			String str = "00" + r.nextInt(10) + "099999" + r.nextInt(10)
					+ "0000199" + r.nextInt(10) + "NTEM+" + r.nextInt(10)
					+ "+99999+0000" + r.nextInt(10) + "\n";
			out.write(str.getBytes());
		}
		out.flush();
		out.close();
	}
}
