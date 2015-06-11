package com.hn.opensource.lucene.Three_Five;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
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
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

public class IndexUtil
{
	private String[] ids = { "1", "2", "3", "4", "5", "6" };
	private String[] emails = { "aa@itat.org", "bb@itat.org", "cc@cc.org",
			"dd@sina.org", "ee@zttc.edu", "ff@itat.org" };
	private String[] contents = { "welcome to visited the space,I like book",
			"hello boy, I like pingpeng ball", "my name is cc I like game",
			"I like football", "I like football and I like basketball too",
			"I like movie and swim" };
	private Date[] dates = null;
	private int[] attachs = { 2, 3, 1, 4, 5, 5 };
	private String[] names = { "zhangsan", "lisi", "john", "jetty", "mike",
			"jake" };
	private Directory directory = null;
	private Map<String, Float> scores = new HashMap<String, Float>();
	// 整个生命周期都有一个 getSearcher(); 如果Writer重新建了索引;
	// 建完项目就会把reader放到application里,只有一个; Writer也有可能一个或多个在线程池中,也不要关闭
	private static IndexReader reader = null;

	public IndexUtil()
	{
		try
		{
			setDates();
			// 加权 谁的权值大就把谁放到前台了; 这样的话, 会先显示2.f的/也就是说itat.org会显示在最前面
			scores.put("itat.org", 2.0f);
			scores.put("zttc.edu", 1.5f);
			directory = FSDirectory.open(new File(
					"/opt/hn/developTools/eclipseWKSP/lu/index/one"));
			// directory = new RAMDirectory();
			index();
			reader = IndexReader.open(directory, false);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public IndexSearcher getSearcher()
	{
		try
		{
			if (reader == null)
			{
				// 防止索引发生改变了, 这里就会重新打开一个
				reader = IndexReader.open(directory, false);
			} else
			{
				IndexReader tr = IndexReader.openIfChanged(reader);
				if (tr != null)
				{
					// 这里需要把原来的reader关闭了
					reader.close();
					reader = tr;
				}
			}
			return new IndexSearcher(reader);
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;

	}

	private void setDates()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			dates = new Date[ids.length];
			dates[0] = sdf.parse("2010-02-19");
			dates[1] = sdf.parse("2012-01-11");
			dates[2] = sdf.parse("2011-09-19");
			dates[3] = sdf.parse("2010-12-22");
			dates[4] = sdf.parse("2012-01-01");
			dates[5] = sdf.parse("2011-05-19");
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public void undelete()
	{
		// 使用IndexReader进行恢复
		try
		{
			IndexReader reader = IndexReader.open(directory, false);
			// 恢复时必须把IndexReader的只读(readOnly)设置为false
			reader.undeleteAll();
			reader.close();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (StaleReaderException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void merge()
	{
		IndexWriter writer = null;
		try
		{
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			// 会将索引合并为2段,这二段中被删除的数据会被清空
			// 特别注意:在3.5之后不建议使用,因为会消耗大量开销;lucene会根据情况自动清空
			writer.forceMerge(2);
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void forceDelete()
	{
		IndexWriter writer = null;

		try
		{
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.forceMergeDeletes();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void delete()
	{
		IndexReader reader = null;

		try
		{
			reader = IndexReader.open(directory, false);
			// 参数是一个选项,可以是一个Query，也可以是一个Term;Term是一个精确查找的值
			// 此时删除的文档并不会完全删除,只是被放到回收站中了,可以回复的
			reader.deleteDocuments(new Term("id", "5"));
			// reader.commit();
			reader.flush();
			reader.close();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			/*
			 * try { if (writer != null) writer.close(); } catch
			 * (CorruptIndexException e) { e.printStackTrace(); } catch
			 * (IOException e) { e.printStackTrace(); }
			 */}
	}

	public void delete02()
	{
		try
		{
			reader.deleteDocuments(new Term("id", "1"));
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void update()
	{
		IndexWriter writer = null;
		try
		{
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			/*
			 * Lucene并没有提供更新,这里更新其实是二个操作的合集 先删除后添加
			 */
			Document doc = new Document();
			doc.add(new Field("id", "11", Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS));
			doc.add(new Field("email", emails[0], Field.Store.YES,
					Field.Index.NOT_ANALYZED));
			doc.add(new Field("content", contents[0], Field.Store.NO,
					Field.Index.ANALYZED));
			doc.add(new Field("name", names[0], Field.Store.YES,
					Field.Index.NOT_ANALYZED_NO_NORMS));
			writer.updateDocument(new Term("id", "1"), doc);
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void query()
	{
		try
		{
			IndexReader reader = IndexReader.open(directory);
			// 文档对象数Document对象; 通过Reader有效的获取文档的数量
			System.out.println("numDocs:" + reader.numDocs());
			System.out.println("maxDocs:" + reader.maxDoc());
			System.out.println("deleteDocs:" + reader.numDeletedDocs());
			reader.close();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void index()
	{
		IndexWriter writer = null;
		try
		{
			writer = new IndexWriter(directory, new IndexWriterConfig(
					Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();
			Document doc = null;
			for (int i = 0; i < ids.length; i++)
			{
				doc = new Document();

				doc.add(new Field("id", ids[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				doc.add(new Field("email", emails[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED));
				doc.add(new Field("email", "test" + i + "@test.com",
						Field.Store.YES, Field.Index.NOT_ANALYZED));
				doc.add(new Field("content", contents[i], Field.Store.NO,
						Field.Index.ANALYZED));
				doc.add(new Field("name", names[i], Field.Store.YES,
						Field.Index.NOT_ANALYZED_NO_NORMS));
				// 存储数字; 附件数
				doc.add(new NumericField("attach", Field.Store.YES, true)
						.setIntValue(attachs[i]));
				// 存储日期
				doc.add(new NumericField("date", Field.Store.YES, true)
						.setLongValue(dates[i].getTime()));
				// 邮件名称
				String et = emails[i].substring(emails[i].lastIndexOf("@") + 1);
				// System.out.println(et);
				// 如果是公司的邮件加权
				if (scores.containsKey(et))
				{
					doc.setBoost(scores.get(et));
				} else
				{
					// 降下来
					doc.setBoost(0.5f);
				}
				// 通过IndexWriter添加文档到索引中
				writer.addDocument(doc);

			}
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (LockObtainFailedException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (writer != null)
					writer.close();
			} catch (CorruptIndexException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void search01()
	{
		try
		{
			IndexReader reader = IndexReader.open(directory);
			IndexSearcher searcher = new IndexSearcher(reader);
			// new Term("content", "like") 含有like
			// 搜索emila 为 test0@test.com
			// TermQuery query = new TermQuery(new Term("email",
			// "test0@test.com"));
			TermQuery query = new TermQuery(new Term("content", "like"));
			TopDocs tds = searcher.search(query, 10);
			for (ScoreDoc sd : tds.scoreDocs)
			{
				// Doc对象不一样, 这里获取的权值getBoost与我们设置的不一样
				Document doc = searcher.doc(sd.doc);
				System.out.println("(" + sd.doc + "-" + doc.getBoost() + "-"
						+ sd.score + ")" + doc.get("name") + "["
						+ doc.get("email") + "]-->" + doc.get("id") + ","
						+ doc.get("attach") + "," + doc.get("date") + ","
						+ doc.getValues("email")[1]);
			}
			reader.close();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void search02()
	{
		try
		{
			IndexSearcher searcher = getSearcher();
			TermQuery query = new TermQuery(new Term("content", "like"));
			TopDocs tds = searcher.search(query, 10);
			for (ScoreDoc sd : tds.scoreDocs)
			{
				Document doc = searcher.doc(sd.doc);
				System.out.println(doc.get("id") + "---->" + doc.get("name")
						+ "[" + doc.get("email") + "]-->" + doc.get("id") + ","
						+ doc.get("attach") + "," + doc.get("date") + ","
						+ doc.getValues("email")[1]);
			}
			// 这里不能关闭reader了,
			searcher.close();
		} catch (CorruptIndexException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
