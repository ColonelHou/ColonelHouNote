package com.hn.java.算法.排序;

import java.util.Arrays;



/**
 * 将数列分为有序和无序两个部分，
 * 每次处理就是将无序数列的第一个元素与有序数列的元素从后往前逐个进行比较，
 * 找出插入位置，将该元素插入到有序数列的合适位置中
 * @author John
 *
 */
public class 插入排序 {

	public static void main(String[] args) {
		int[] list = { 49, 38, 65, 345, 43, 13, 27, 2, 54353 };
		sort(list);
		System.out.println(Arrays.toString(list));
	}

	public static void sort(int data[]) {
		for (int i = 1; i < data.length; i++) {
			for (int j = i; j > 0; j--) {
				if (data[j] < data[j - 1]) {
					int temp = data[j];
					data[j] = data[j - 1];
					data[j - 1] = temp;
				}
			}
		}
	}
}
