package com.hn.java.se.集合.compare;

import java.util.Map;

import java.util.HashMap;

public class HashmapTest {

	public static void main(String[] args) {

		Map<Integer, Integer> hashmap = new HashMap<Integer, Integer>();

		int tt = 13;
		long begin1 = System.currentTimeMillis();
		for (int i = 0; i < 5000000; i++) {

			tt = Math.abs(tt * (tt - i) - 119);

			hashmap.put(tt, tt);

			// System.out.println(hashmap.get(tt));

		}

		System.out.println("time=" + (System.currentTimeMillis() - begin1)
				+ "ms.");

	}

}
