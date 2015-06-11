package com.hn.java.算法.位图法;

public class BitMap {
	public static void main(String[] args) {
		int[][] arr = { { 1, 2, 3, 5, 3, 5, 56, 534, 3, 32 }, { 1, 2, 3, 5 },
				{ 1, 2, 3, 5, 3, 5 }, { 0, 0, 1, 2, 3, 5, 56, 534, 78, 32 }, };
		for (int i = 0; i < arr.length; i++) {
			System.out.print(" 数组: ");
			for (int temp : arr[i]) {
				System.out.print(temp + ", ");
			}
			System.out.print(" 中 ");
			System.out.print(hasDuplicatedItem(arr[i]) ? " 存在 " : " 不存在 ");
			System.out.print(" 重复元素.\n ");
		}
	}

	/**
	 * 判断整形数组中是否有重复数据，时间复杂度为O（n）
	 * 
	 * @param arr
	 * @return
	 */
	public static boolean hasDuplicatedItem(int[] arr) {
		// 扫描数组找最大值
		int max = arr[0];
		for (int i = 1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		// 按最大值创建一个新数组
		int[] bitArray = new int[max + 1];
		// 按值向新数组中添值，如value为3则bitArray[3]=1
		for (int value : arr) {
			if (bitArray[value] != 0) {
				// 如果value指向的位置已经不是零，说明之前已经给这一块置1了，立即返回true表示数组有重复
				return true;
			} else {
				// value指向的位置是零,则置为1表示这一位已经有数存在了
				bitArray[value] = 1;
			}
		}
		return false;
	}
}
