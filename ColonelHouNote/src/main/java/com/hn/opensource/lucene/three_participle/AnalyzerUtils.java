package com.hn.opensource.lucene.three_participle;
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;


public class AnalyzerUtils
{

	public static void displayToken(String str, Analyzer a)
	{
		try
		{
			// 前面词汇过虑等, 最后生成TokenStream; content没意义; 只是为了显示分词
			TokenStream stream = a
					.tokenStream("content", new StringReader(str));
			// 保存相应的词汇; 创建一个属性 ,这个属性就添加到流中, 随着这个TokenStream增加
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			// 取出流中
			while (stream.incrementToken())
			{
				System.out.print("[" + cta + "]");
			}
			System.out.println();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 显示所有的重要信息
	 * @param str
	 * @param a
	 */
	public static void displayAllTokenInfo(String str, Analyzer a)
	{
		try
		{
			//从这条流里获取所有的东西
			TokenStream stream = a
					.tokenStream("content", new StringReader(str));
			// 位置增量的属性, 存储语汇单元之间距离
			PositionIncrementAttribute pia = stream
					.addAttribute(PositionIncrementAttribute.class);
			// 偏移量;每个语汇单元的位置偏移量
			OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
			// 存储每个语汇的信息(分词单元信息)
			CharTermAttribute cta = stream
					.addAttribute(CharTermAttribute.class);
			// 使用的分词的类型信息
			TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
			for (; stream.incrementToken();)
			{
				//输出位置信息
				System.out.print(pia.getPositionIncrement() + ":");
				//输出我们的单词信息; 从多少到多少; 输出类型
				System.out.print(cta + "[" + oa.startOffset() + "-"
						+ oa.endOffset() + "]-->" + ta.type() + "\n");
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
