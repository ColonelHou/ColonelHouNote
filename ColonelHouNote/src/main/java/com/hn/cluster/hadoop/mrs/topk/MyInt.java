package com.hn.cluster.hadoop.mrs.topk;

public class MyInt implements Comparable<MyInt>
{
	private Integer value;

	public MyInt(Integer value)
	{
		this.value = value;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

	@Override
	public int compareTo(MyInt o)
	{
		return value.compareTo(o.getValue());
	}
}
