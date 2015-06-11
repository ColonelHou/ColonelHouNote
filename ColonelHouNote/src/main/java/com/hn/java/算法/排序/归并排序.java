package com.hn.java.算法.排序;

public class 归并排序 {
	public static void sort(int data[], int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			sort(data, start, mid);
			sort(data, mid + 1, end);
			merge(data, start, mid, end);
		}
	}

	public static void merge(int data[], int start, int mid, int end) {
		int temp[] = new int[end - start + 1];
		int i = start;
		int j = mid + 1;
		int k = 0;
		while (i <= mid && j <= end) {
			if (data[i] < data[j]) {
				temp[k++] = data[i++];
			} else {
				temp[k++] = data[j++];
			}
		}

		while (i <= mid) {
			temp[k++] = data[i++];
		}
		while (j <= end) {
			temp[k++] = data[j++];
		}

		for (k = 0, i = start; k < temp.length; k++, i++) {
			data[i] = temp[k];
		}
	}
}
