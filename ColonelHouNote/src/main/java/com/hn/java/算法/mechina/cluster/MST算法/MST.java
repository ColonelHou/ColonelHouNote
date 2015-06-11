package com.hn.java.算法.mechina.cluster.MST算法;

import java.util.ArrayList;
import java.util.List;

public class MST
{

	private List<City> data;

	private double[][] ds;

	public MST(List<City> data)
	{
		this.data = data;
	}

	public List<Edge> compute()
	{
		// 距离矩阵
		ds = new double[data.size()][data.size()];

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

		boolean[] isMst = new boolean[data.size()];
		isMst[0] = true;
		Edge edge = null;
		List<Edge> edges = new ArrayList<Edge>();
		while ((edge = findMinEdge(isMst)) != null)
		{
			edges.add(edge);

			// 标记为已知MST数据点
			isMst[edge.getJ()] = true;
		}
		return edges;
	}

	// 找出 和 已知的MST数据点 最小距离的点
	private Edge findMinEdge(boolean[] isMst)
	{
		// 初始化无限大
		double minds = Double.POSITIVE_INFINITY;
		int minI = -1;
		int minJ = -1;
		Edge edge = null;
		for (int i = 0; i < ds.length; i++)
		{
			if (isMst[i] == true)
			{
				for (int j = 0; j < ds.length; j++)
				{
					if (isMst[j] == false)
					{
						if (minds > ds[i][j])
						{
							minds = ds[i][j];
							minI = i;
							minJ = j;
						}
					}
				}
			}
		}
		if (minI > -1)
		{
			edge = new Edge(minI, minJ, minds);
		}
		return edge;
	}

	// 计算空间距离
	private double getDistance(City city1, City city2)
	{
		double distance = Math.pow(city1.getX() - city2.getX(), 2)
				+ Math.pow(city1.getY() - city2.getY(), 2);
		return Math.sqrt(distance);

	}
}
