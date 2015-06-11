package com.hn.lu.three_participle;

import java.io.Reader;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LetterTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.util.Version;

public class MyStopAnalyzer extends Analyzer {
	@SuppressWarnings("rawtypes")
	private Set stops;
	@SuppressWarnings("unchecked")
	
	/**
	 * 
	 * @param sws 需要进行停用的词
	 */
	public MyStopAnalyzer(String[]sws) {
		//打印原来有的停用词
		System.out.println(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
		
		//会自动将字符串数组转换为Set
		stops = StopFilter.makeStopSet(Version.LUCENE_35, sws, true);
		//把原来的停用词也加进来
		stops.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
	}
	
	public MyStopAnalyzer() {
		//停用词就是原来的停用词
		stops = StopAnalyzer.ENGLISH_STOP_WORDS_SET;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		//Tokenizer
		return new StopFilter(Version.LUCENE_35,
			   new LowerCaseFilter(Version.LUCENE_35, 
			   new LetterTokenizer(Version.LUCENE_35,reader)), stops);
	}

}
