package com.hn.java.se.集合;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;


public class HashMapTest {

	public static void main(String[] args) {
		Code c1 = new Code(1);
		Code c2 = new Code(1);
		System.out.println(c1.hashCode());
		System.out.println(c2.hashCode());
		System.out.println(c1.equals(c2));
		Map<Code, String> map = new HashMap<Code, String>();
		map.put(c1, "c1aaaaa");
		map.put(c2, "c1bbbbb");
		System.out.println(map.toString());
	}
	
	public static class Code{
		private int a;

		public Code() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Code(int a) {
			super();
			this.a = a;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + a;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Code other = (Code) obj;
			if (a != other.a)
				return false;
			return true;
		}
		
	}
}
