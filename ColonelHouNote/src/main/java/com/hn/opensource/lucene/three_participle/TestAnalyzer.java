package com.hn.lu.three_participle;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.StopAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;

public class TestAnalyzer {

	
	public static void main(String[] args)
	{
		test05();
	}
	/**
	 * [my][house][i][am][come][from][yunnang][zhaotong][my][email][ynkonghao][gmail.com][my][qq][64831031]
     * [my][house][i][am][come][from][yunnang][zhaotong][my][email][ynkonghao][gmail][com][my][qq]
     * [this][is][my][house][i][am][come][from][yunnang][zhaotong][my][email][is][ynkonghao][gmail][com][my][qq][is]
     * [this][is][my][house,I][am][come][from][yunnang][zhaotong,My][email][is][ynkonghao@gmail.com,My][QQ][is][64831031]
     * 
     * 
	 * [我][目][前][在][职][于][北][京][市]
     * [我目前在职于北京市]
     * [我目前在职于北京市]
     * [我目前在职于北京市]
	 * 
	 * String txt = "我目前在职于北京市";
	 */
	public static void  test01() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);//数字取消了
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);//
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		String txt = "this is my house,I am come from yunnang zhaotong," +
				"My email is ynkonghao@gmail.com,My QQ is 64831031";
		
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
		AnalyzerUtils.displayToken(txt, a3);
		AnalyzerUtils.displayToken(txt, a4);
	}
	
	
	public static void  test02() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		Analyzer a5 = new MMSegAnalyzer(new File("/opt/hn/hadoop_family/lucene/src/03_lucene_analyzer/lib/data"));
		String txt = "我来自中国北京市海淀侯上校";
		
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
		AnalyzerUtils.displayToken(txt, a3);
		AnalyzerUtils.displayToken(txt, a4);
		AnalyzerUtils.displayToken(txt, a5);
	}
	
	/**
	 * 标准分词器
	 * 1:how[0-3]--><ALPHANUM>
     * 2:you[8-11]--><ALPHANUM>
     * 1:thank[12-17]--><ALPHANUM>
     * 1:you[18-21]--><ALPHANUM>
     * -------------分词器-----------------
     * 1:how[0-3]-->word
     * 2:you[8-11]-->word
     * 1:thank[12-17]-->word
     * 1:you[18-21]-->word
     * --------------分词器----------------
     * 1:how[0-3]-->word
     * 1:are[4-7]-->word
     * 1:you[8-11]-->word
     * 1:thank[12-17]-->word
     * 1:you[18-21]-->word
     * ---------------分词器---------------
     * 1:how[0-3]-->word
     * 1:are[4-7]-->word
     * 1:you[8-11]-->word
     * 1:thank[12-17]-->word
     * 1:you[18-21]-->word
	 */
	public static void  test03() {
		Analyzer a1 = new StandardAnalyzer(Version.LUCENE_35);
		Analyzer a2 = new StopAnalyzer(Version.LUCENE_35);
		Analyzer a3 = new SimpleAnalyzer(Version.LUCENE_35);
		Analyzer a4 = new WhitespaceAnalyzer(Version.LUCENE_35);
		String txt = "how are you thank you";
		
		AnalyzerUtils.displayAllTokenInfo(txt, a1);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a2);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a3);
		System.out.println("------------------------------");
		AnalyzerUtils.displayAllTokenInfo(txt, a4);
		
	}
	
	
	public static void  test04() {
		//把I you hate全过虑掉
		Analyzer a1 = new MyStopAnalyzer(new String[]{"I","you","hate"});
		Analyzer a2 = new MyStopAnalyzer();
		String txt = "how are you thank you I hate you";
		AnalyzerUtils.displayToken(txt, a1);
		AnalyzerUtils.displayToken(txt, a2);
	}
	
	
	public static void  test05() {
		try {
			Analyzer a2 = new MySameAnalyzer(new SimpleSamewordContext2());
			String txt = "我来自中国北京市海淀侯上校";
			Directory dir = new RAMDirectory();
			IndexWriter writer = new IndexWriter(dir,new IndexWriterConfig(Version.LUCENE_35, a2));
			Document doc = new Document();
			doc.add(new Field("content",txt,Field.Store.YES,Field.Index.ANALYZED));
			writer.addDocument(doc);
			writer.close();
			IndexSearcher searcher = new IndexSearcher(IndexReader.open(dir));
			TopDocs tds = searcher.search(new TermQuery(new Term("content","中国")),10);
			Document d = searcher.doc(tds.scoreDocs[0].doc);
			System.out.println(d.get("content"));
			AnalyzerUtils.displayAllTokenInfo(txt, a2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
