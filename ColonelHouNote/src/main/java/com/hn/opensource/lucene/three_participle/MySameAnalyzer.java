package com.hn.opensource.lucene.three_participle;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import com.chenlb.mmseg4j.Dictionary;
import com.chenlb.mmseg4j.MaxWordSeg;
import com.chenlb.mmseg4j.analysis.MMSegTokenizer;


/**
 * 自定义分词器 
 * @author hadoop
 *
 */
public class MySameAnalyzer extends Analyzer {
	private SamewordContext samewordContext;
	
	public MySameAnalyzer(SamewordContext swc) {
		samewordContext = swc;
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		Dictionary dic = Dictionary.getInstance("/opt/hn/hadoop_family/lucene/src/03_lucene_analyzer/lib/data");
		return new MySameTokenFilter(
				new MMSegTokenizer(new MaxWordSeg(dic), reader),samewordContext);
	}

}
