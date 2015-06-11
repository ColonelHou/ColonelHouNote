package com.hn.java.算法.mechina.cluster.kmeans;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 阿飞哥
 * 
 */
public class Kmeans<T>
{

	/**
	 * 所有数据列表
	 */
	private List<T> players = new ArrayList<T>();

	/**
	 * 数据类别
	 */
	private Class<T> classT;

	/**
	 * 初始化列表
	 */
	private List<T> initPlayers;

	/**
	 * 需要纳入kmeans算法的属性名称
	 */
	private List<String> fieldNames = new ArrayList<String>();

	/**
	 * 分类数
	 */
	private int k = 1;

	public Kmeans()
	{

	}

	/**
	 * 初始化列表
	 * 
	 * @param list
	 * @param k
	 */
	public Kmeans(List<T> list, int k)
	{
		this.players = list;
		this.k = k;
		T t = list.get(0);
		this.classT = (Class<T>) t.getClass();
		Field[] fields = this.classT.getDeclaredFields();
		for (int i = 0; i < fields.length; i++)
		{
			Annotation kmeansAnnotation = fields[i]
					.getAnnotation(KmeanField.class);
			if (kmeansAnnotation != null)
			{
				fieldNames.add(fields[i].getName());
			}

		}

		initPlayers = new ArrayList<T>();
		for (int i = 0; i < k; i++)
		{
			initPlayers.add(players.get(i));
		}
	}

	public List<T>[] comput()
	{
		List<T>[] results = new ArrayList[k];

		boolean centerchange = true;
		while (centerchange)
		{
			centerchange = false;
			for (int i = 0; i < k; i++)
			{
				results[i] = new ArrayList<T>();
			}
			//总的来说就是: 找出每个球员到三个点中那个点距离最小,并把此球员添加到此点下标的集合当中
			for (int i = 0; i < players.size(); i++)
			{
				T p = players.get(i);
				double[] dists = new double[k];
				//循环找出最这个球员到三个点的距离
				for (int j = 0; j < initPlayers.size(); j++)
				{
					T initP = initPlayers.get(j);
					/* 计算距离 */
					double dist = distance(initP, p);
					dists[j] = dist;
				}
				//找出此球员到三个点中最近距离的点下标, 并把此球员添加到此点下标的集合中
				int dist_index = computOrder(dists);
				results[dist_index].add(p);
			}

			for (int i = 0; i < k; i++)
			{
				//求出此点(三个点的集合)中的平均分值, 并创建新的球员对象
				T player_new = findNewCenter(results[i]);
				//取出集合中点的值, 
				T player_old = initPlayers.get(i);
				//比较新创建的点与旧点是否相等; 临界点:直到取到三集合中各平均值与各自集合值相等为止
				if (!IsPlayerEqual(player_new, player_old))
				{
					//如果不相等就把原来指定三点的集合对应下标值设置为上面取到的此下标的平均值球员
					centerchange = true;
					initPlayers.set(i, player_new);
				}

			}

		}

		return results;
	}

	/**
	 * 比较是否两个对象是否属性一致
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public boolean IsPlayerEqual(T p1, T p2)
	{
		if (p1 == p2)
		{
			return true;
		}
		if (p1 == null || p2 == null)
		{
			return false;
		}

		boolean flag = true;
		try
		{
			for (int i = 0; i < fieldNames.size(); i++)
			{
				String fieldName = fieldNames.get(i);
				String getName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Object value1 = invokeMethod(p1, getName, null);
				Object value2 = invokeMethod(p2, getName, null);
				if (!value1.equals(value2))
				{
					flag = false;
					break;
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			flag = false;
		}

		return flag;
	}

	/**
	 * 得到新聚类中心对象
	 * 
	 * @param ps
	 * @return
	 */
	public T findNewCenter(List<T> ps)
	{
		try
		{
			T t = classT.newInstance();
			if (ps == null || ps.size() == 0)
			{
				return t;
			}

			double[] ds = new double[fieldNames.size()];
			for (T vo : ps)
			{
				for (int i = 0; i < fieldNames.size(); i++)
				{
					String fieldName = fieldNames.get(i);
					String getName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Object obj = invokeMethod(vo, getName, null);
					Double fv = (obj == null ? 0 : Double.parseDouble(obj + ""));
					ds[i] += fv;
				}

			}

			for (int i = 0; i < fieldNames.size(); i++)
			{
				ds[i] = ds[i] / ps.size();
				String fieldName = fieldNames.get(i);

				/* 给对象设值 */
				String setName = "set"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				invokeMethod(t, setName, new Class[]
				{ double.class }, ds[i]);

			}

			return t;
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;

	}

	/**
	 * 得到最短距离，并返回最短距离索引
	 * 
	 * @param dists
	 * @return
	 */
	public int computOrder(double[] dists)
	{
		double min = 0;
		int index = 0;
		for (int i = 0; i < dists.length - 1; i++)
		{
			double dist0 = dists[i];
			if (i == 0)
			{
				min = dist0;
				index = 0;
			}
			double dist1 = dists[i + 1];
			if (min > dist1)
			{
				min = dist1;
				index = i + 1;
			}
		}

		return index;
	}

	/**
	 * 计算距离（相似性） 采用欧几里得算法
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	public double distance(T p0, T p1)
	{
		double dis = 0;
		try
		{

			for (int i = 0; i < fieldNames.size(); i++)
			{
				String fieldName = fieldNames.get(i);
				String getName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				Double field0Value = Double.parseDouble(invokeMethod(p0,
						getName, null) + "");
				Double field1Value = Double.parseDouble(invokeMethod(p1,
						getName, null) + "");
				dis += Math.pow(field0Value - field1Value, 2);
			}

		} catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return Math.sqrt(dis);

	}

	/*------公共方法-----*/
	public Object invokeMethod(Object owner, String methodName,
			Class[] argsClass, Object... args)
	{
		Class ownerClass = owner.getClass();
		try
		{
			Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
			return method.invoke(owner, args);
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return null;
	}

}
