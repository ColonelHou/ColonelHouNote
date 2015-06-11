package com.hn.java.模式.装饰器模式.大话;

import java.util.List;

public class VarianceCalculator extends OptionalCalculator
{

	@Override
	public double getResult(List<Double> list)
	{
		double result = calculator.getResult(list);

		if (list.size() > 4)
		{
			double num = 0;
			for (int i = 0; i < list.size(); i++)
			{
				num += (list.get(i) - result) * (list.get(i) - result);
			}
			System.out.println(list.size() + "个数的方差是 " + (num / list.size()));
		} else
		{
			System.out.println("样本数量不足20个不计算方差");
		}

		return result;
	}
}