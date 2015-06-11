package com.hn.java.模式.装饰器模式.大话;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		/*
		 * Scanner in = new Scanner(System.in); ArrayList<Double> list = new
		 * ArrayList<Double>();
		 * 
		 * double d; do { d = in.nextDouble(); if (d >= 0){ list.add(d); } }
		 * while (d >= 0);
		 */

		ArrayList<Double> list = new ArrayList<Double>();
		list.add(2.0);
		list.add(2.0);
		list.add(3.0);
		list.add(4.0);
		list.add(5.0);
		list.add(6.0);

		Calculator ave = new AverageCalculator();
		OptionalCalculator over = new OverAverageCalculator();
		OptionalCalculator var = new VarianceCalculator();

		over.setCalculator(ave);
		var.setCalculator(over);

		System.out.println("平均值：" + var.getResult(list));
	}

}
