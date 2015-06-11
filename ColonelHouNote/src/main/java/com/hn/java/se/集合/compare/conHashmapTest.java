package com.hn.java.se.集合.compare;

import java.util.concurrent.ConcurrentHashMap;

public class conHashmapTest {
	public static void main(String[] args) {

		ConcurrentHashMap<Integer, Integer> chashmap = new ConcurrentHashMap<Integer, Integer>();

		int tt = 13;

		long begin1 = System.currentTimeMillis();

		for (int i = 0; i < 5000000; i++) {

			tt = Math.abs(tt * (tt - i) - 119);

			chashmap.put(tt, tt);

			// System.out.println(hashmap.get(tt));

		}

		System.out.println("time=" + (System.currentTimeMillis() - begin1)
				+ "ms.");

	}
}
