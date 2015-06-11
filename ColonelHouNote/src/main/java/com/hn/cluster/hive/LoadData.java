package com.hn.cluster.hive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class LoadData
{

	public static void main(String[] args)
	{

		/*
		 * for (int i = 1; i <= 1000; i++) { //System.out.println((10 + i ) +
		 * "\t" + (10 + i ) + "\t" + (1389111000 + i));
		 * //System.out.println((20140413 + i + "," + "jim" + i + "," + new
		 * SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		 * //System.out.println( i + "," + (i + 10) + "," + ("jim" + i));
		 * System.out.println(i + "," + "jim" + i); }
		 */
		List<Integer> temps = new ArrayList<Integer>();
		temps.add(56);
		temps.add(54);
		temps.add(78);
		temps.add(1342);
		temps.add(4);
		temps.add(987);
		temps.add(567);
		temps.add(123);
		temps.add(344);

		Iterator<Integer> iter = temps.iterator();
		int max = Integer.MIN_VALUE;
		while (iter.hasNext())
		{
			max = Math.max(max, iter.next());
		}
		System.out.println(max);
	}
}
