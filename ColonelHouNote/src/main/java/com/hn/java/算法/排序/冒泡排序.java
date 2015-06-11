package com.hn.java.算法.排序;

public class 冒泡排序
{

	/**
	 * 从开始拿到相邻的两个数进行比较， 如果前面的值大于后面的值，那就把值进行交换一下
	 * 
	 * @param data
	 */
	public void bubble(Integer[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			for (int j = 0; j < data.length - 1 - i; j++)
			{
				if (data[j] > data[j + 1])
				{ // 如果后一个数小于前一个数交换
					int tmp = data[j];
					data[j] = data[j + 1];
					data[j + 1] = tmp;
				}
			}
		}
	}

	public static void main(String[] args)
	{
		Integer[] list = { 49, 38, 65, 345, 43, 13, 27, 2, 54353 };
		冒泡排序 bs = new 冒泡排序();
		bs.bubble(list);
		for (int i = 0; i < list.length; i++)
		{
			System.out.print(list[i] + " ");
		}
	}
}
