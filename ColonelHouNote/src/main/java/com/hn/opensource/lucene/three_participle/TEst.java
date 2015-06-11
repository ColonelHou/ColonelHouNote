package com.hn.lu.three_participle;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

public class TEst
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		test01();
	}
	public static void test01() {
		Analyzer a1 = new MyStopAnalyzer(new String[]{"I","you","hate"});
		Analyzer a2 = new MyStopAnalyzer();
		String txt = "how are you thank you I hate you";
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
	}
}
