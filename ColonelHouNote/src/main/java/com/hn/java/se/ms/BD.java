package com.hn.java.se.ms;

public class BD {

	public static class A
	{
		int i ;

		public A() {
			super();
			// TODO Auto-generated constructor stub
		}

		public A(int i) {
			super();
			this.i = i;
		}

		public int getI() {
			return i;
		}

		public void setI(int i) {
			this.i = i;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return super.toString();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + i;
			return result;
		}

		/*@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			A other = (A) obj;
			if (i != other.i)
				return false;
			return true;
		}*/
		
		
	}
	public BD() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
	
		//System.out.println(getVal(5));
		A a = new A(1);
		A a1 = new A(1);
		A b = new A(2);
		
		A c = new A(100);
		A d = new A(10000);
		A e = new A(10001);
		System.out.println(a.hashCode());
		System.out.println(a1.hashCode());
		System.out.println("BD.main()" + a.equals(a1));
		System.out.println(b.hashCode());
		System.out.println(c.hashCode());
		System.out.println(d.hashCode());
		System.out.println(e.hashCode());
	}
	
	/**
	 * 实现一个函数，对一个正整数n，算得到1需要的最少操作次数。操作规则为：如果n为偶数，将其除以2；
	 * 如果n为奇数，可以加1或减1；一直处理下去。
	 * @param n
	 * @return
	 */
	public static int getVal(int n)
	{
		if(n == 1 )
			return 0;
		if(n % 2 == 0)
		{
			return 1 + getVal(n / 2);
		}
		int x = getVal(n + 1);
		int y = getVal(n - 1);
		if(x > y)
		{
			return y + 1;
		}
		else
		{
			return x + 1;
		}
	}
}
