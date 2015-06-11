package com.hn.java.模式.装饰器模式.大话;

import java.util.List;

public class OverAverageCalculator extends OptionalCalculator
{

	@Override
	public double getResult(List<Double> list)
	{
		double result = calculator.getResult(list);

		int count = 0;
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) >= result)
			{
				count++;
			}
		}
		System.out.println(count + "个高于平均值");

		return result;
	}

}
