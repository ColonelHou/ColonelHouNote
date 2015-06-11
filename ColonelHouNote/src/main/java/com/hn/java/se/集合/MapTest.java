package com.hn.java.se.集合;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapTest {

	public static void main(String[] args) {

		// HashMap
		System.out.println("------HashMap无序输出------");
		HashMap hsMap = new HashMap();
		hsMap.put("3", "Value3");
		hsMap.put("1", "Value1");
		hsMap.put("2", "Value2");
		hsMap.put("b", "ValueB");
		hsMap.put("a", "ValueA");
		Iterator it = hsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			System.out.println("Key: " + e.getKey() + "--Value: "
					+ e.getValue());
		}

		// TreeMap
		System.out.println("------TreeMap按Key排序输出------");
		TreeMap teMap = new TreeMap();
		teMap.put("3", "Value3");
		teMap.put("1", "Value1");
		teMap.put("2", "Value2");
		teMap.put("b", "ValueB");
		teMap.put("a", "ValueA");
		Iterator tit = teMap.entrySet().iterator();
		while (tit.hasNext()) {
			Map.Entry e = (Map.Entry) tit.next();
			System.out.println("Key: " + e.getKey() + "--Value: "
					+ e.getValue());
		}

		// LinkedHashMap
		System.out.println("--LinkedHashMap根据输入的顺序输出--");
		LinkedHashMap lhsMap = new LinkedHashMap();
		lhsMap.put("3", "Value3");
		lhsMap.put("1", "Value1");
		lhsMap.put("2", "Value2");
		lhsMap.put("b", "ValueB");
		lhsMap.put("a", "ValueA");
		Iterator lit = lhsMap.entrySet().iterator();
		while (lit.hasNext()) {
			Map.Entry e = (Map.Entry) lit.next();
			System.out.println("Key: " + e.getKey() + "--Value: "
					+ e.getValue());
		}
	}
}
