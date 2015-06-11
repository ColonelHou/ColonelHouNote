package com.hn.lu.two_search;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StaleReaderException;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;


public class IndexUtil {
	private String[] ids = {"1","2","3","4","5","6"};
	private String[] emails = {"aa@itat.org","bb@itat.org","cc@cc.org","dd@sina.org","ee@zttc.edu","ff@itat.org"};
	private String[] contents = {
			"welcome to visited the space,I like book",
			"hello boy, I like pingpeng ball",
			"my name is cc I like game",
			"I like football",
			"I like football and I like basketball too",
			"I like movie and swim"
	};
	private Date[] dates = null;
	private int[] attachs = {2,3,1,4,5,5};
	private String[] names = {"zhangsan","lisi","john","jetty","mike","jake"};
	private Directory directory = null;
	private Map<String,Float> scores = new HashMap<String,Float>();
	
	public IndexUtil() {
		setDates();
		scores.put("itat.org",2.0f);
		scores.put("zttc.edu", 1.5f);
		directory = new RAMDirectory();
		index();
	}
	
	
	
	private void setDates() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dates = new Date[ids.length];
			dates[0] = sdf.parse("2010-02-19");
			dates[1] = sdf.parse("2012-01-11");
			dates[2] = sdf.parse("2011-09-19");
			dates[3] = sdf.parse("2010-12-22");
			dates[4] = sdf.parse("2012-01-01");
			dates[5] = sdf.parse("2011-05-19");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public void undelete() {
		//ʹ��IndexReader���лָ�
		try {
			IndexReader reader = IndexReader.open(directory,false);
			//�ָ�ʱ�������IndexReader��ֻ��(readOnly)����Ϊfalse
			reader.undeleteAll();
			reader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (StaleReaderException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void merge() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
			//�Ὣ����ϲ�Ϊ2�Σ��������еı�ɾ�����ݻᱻ���
			//�ر�ע�⣺�˴�Lucene��3.5֮�󲻽���ʹ�ã���Ϊ����Ĵ����Ŀ���
			//Lucene��������Զ������
			writer.forceMerge(2);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null) writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void forceDelete() {
		IndexWriter writer = null;
		
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
			writer.forceMergeDeletes();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null) writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void delete() {
		IndexWriter writer = null;
		
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
			//������һ��ѡ�������һ��Query��Ҳ������һ��term��term��һ����ȷ���ҵ�ֵ
			//��ʱɾ����ĵ������ᱻ��ȫɾ����Ǵ洢��һ������վ�еģ����Իָ�
			writer.deleteDocuments(new Term("id","1"));
			writer.commit();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null) writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void update() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory,
					new IndexWriterConfig(Version.LUCENE_35,new StandardAnalyzer(Version.LUCENE_35)));
			/*
			 * Lucene��û���ṩ���£�����ĸ��²�����ʵ���������������ĺϼ�
			 * ��ɾ��֮�������
			 */
			Document doc = new Document();
			doc.add(new Field("id","11",Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("email",emails[0],Field.Store.YES,Field.Index.NOT_ANALYZED));
			doc.add(new Field("content",contents[0],Field.Store.NO,Field.Index.ANALYZED));
			doc.add(new Field("name",names[0],Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
			writer.updateDocument(new Term("id","1"), doc);
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null) writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void query() {
		try {
			IndexReader reader = IndexReader.open(directory);
			//ͨ��reader������Ч�Ļ�ȡ���ĵ�������
			System.out.println("numDocs:"+reader.numDocs());
			System.out.println("maxDocs:"+reader.maxDoc());
			System.out.println("deleteDocs:"+reader.numDeletedDocs());
			reader.close();
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void index() {
		IndexWriter writer = null;
		try {
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();
			Document doc = null;
			for(int i=0;i<ids.length;i++) {
				doc = new Document();
				doc.add(new Field("id",ids[i],Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email",emails[i],Field.Store.YES,Field.Index.NOT_ANALYZED));
				doc.add(new Field("content",contents[i],Field.Store.NO,Field.Index.ANALYZED));
				doc.add(new Field("name",names[i],Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
				//�洢����
				doc.add(new NumericField("attach",Field.Store.YES,true).setIntValue(attachs[i]));
				//�洢����
				doc.add(new NumericField("date",Field.Store.YES,true).setLongValue(dates[i].getTime()));
				String et = emails[i].substring(emails[i].lastIndexOf("@")+1);
				System.out.println(et);
				if(scores.containsKey(et)) {
					doc.setBoost(scores.get(et));
				} else {
					doc.setBoost(0.5f);
				}
				writer.addDocument(doc);
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(writer!=null)writer.close();
			} catch (CorruptIndexException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
