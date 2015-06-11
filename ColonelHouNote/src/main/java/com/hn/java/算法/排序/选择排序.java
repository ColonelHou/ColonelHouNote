package com.hn.java.算法.排序;

import java.util.Arrays;

/**
 * 每一次从待排序的数据元素中选出最小（或最大）的一个元素， 
 * 顺序放在已排好序的数列的最后， 
 * 直到全部待排序的数据元素排完。
 * 
 * @author John
 *
 */
public class 选择排序 {

	public static void main(String[] args) {
		int[] list = { 49, 38, 65, 345, 43, 13, 27, 2, 54353 };
		sort(list);
		System.out.println(Arrays.toString(list));
	}

	public static void sort(int data[]) {
		int minVal;
		int minIndex;
		for (int i = 0; i < data.length - 1; i++) {

			// 找出最小的值与期下标, 并赋给临时变量中
			minVal = data[i];
			minIndex = i;
			for (int j = i + 1; j < data.length; j++) {
				if (data[j] < minVal) {
					minVal = data[j];
					minIndex = j;
				}
			}

			// 如果最小的值与其下标替换了后, 并把相应的值进行替换.
			if (minVal != data[i] && minIndex != i) {
				data[minIndex] = data[i];
				data[i] = minVal;
			}
		}

	}
}
