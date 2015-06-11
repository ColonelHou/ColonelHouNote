package com.hn.java.算法.打印每行并对应数字连续;

public class Simp {

	static int x = 0;
	public static void main(String[] args) {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < i; j++) {
				System.out.print(x ++ + " ");
			}
			System.out.println();
		}
	}
}
