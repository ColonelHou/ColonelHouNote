package com.hn.java.算法.mechina.cluster.单链接算法;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 聚类之 单链接算法
 * 
 * @author duyf
 * 
 */
public class SingleLink
{

	private List<City> data;

	// 默认阀值
	private double distanceX = 8;

	public SingleLink(List<City> list)
	{
		data = list;
	}

	public List<Set<City>> compute()
	{
		List<Set<City>> list = new ArrayList<Set<City>>();

		// 距离矩阵
		double[][] ds = new double[data.size()][data.size()];

		for (int i = 0; i < data.size(); i++)
		{
			City city1 = data.get(i);
			for (int j = i + 1; j < data.size(); j++)
			{
				City city2 = data.get(j);
				ds[i][j] = getDistance(city1, city2);
				// 矩阵 对称性
				ds[j][i] = ds[i][j];
			}
			ds[i][i] = 0.0;
		}

		for (int i = 0; i < ds.length; i++)
		{
			for (int j = 0; j < ds.length; j++)
			{
				System.out.print((int) ds[i][j] + ",");
			}
			System.out.println();
		}

		/**
		 * 把矩阵中的每个要素(就是两个城市的距离)与 默认阀值 比较, 如果小于默认阀值就添加到集合当中
		 */
		boolean[] hasUsed = new boolean[ds.length];
		for (int i = 0; i < ds.length; i++)
		{
			Set<City> setDs = new HashSet<City>();
			if (hasUsed[i])
			{
				continue;
			}
			for (int j = i; j < ds.length; j++)
			{
				if (ds[i][j] <= distanceX && hasUsed[j] == false)
				{
					setDs.add(data.get(j));
					hasUsed[j] = true;
				}
			}
			if (setDs.size() > 0)
			{
				list.add(setDs);
			}

		}
		return list;
	}

	// 计算空间距离
	private double getDistance(City city1, City city2)
	{
		double distance = Math.pow(city1.getX() - city2.getX(), 2)
				+ Math.pow(city1.getY() - city2.getY(), 2);
		return Math.sqrt(distance);

	}

}

