package com.hn.java.正则;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class 字符串中获取手机号码
{

	public static void main(String[] args)
	{
		System.out.println(checkNum("朝秦暮楚15311588609fafe"));
	}

	private static String checkNum(String num)
	{
		if (num == null || num.length() == 0)
		{
			return "";
		}
		Pattern pattern = Pattern
				.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)");
		Matcher matcher = pattern.matcher(num);
		StringBuffer bf = new StringBuffer(64);
		while (matcher.find())
		{
			bf.append(matcher.group()).append(",");
		}
		int len = bf.length();
		if (len > 0)
		{
			bf.deleteCharAt(len - 1);
		}
		return bf.toString();
	}
}
