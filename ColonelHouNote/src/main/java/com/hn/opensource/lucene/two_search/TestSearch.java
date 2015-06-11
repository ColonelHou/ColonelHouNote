package com.hn.lu.two_search;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;

public class TestSearch {
	
	
	public static void main(String[] args) throws ParseException
	{
//		init();
//		searchByQueryParse();
		
		//创建分页索引
		//indexFile();
		
		/*
		 * 查询第二页
		 * init();
		testSearchPage01();*/
		init();
		testSearchPage01();
	}
	private static SearcherUtil su;
	public static void  init() {
		su = new SearcherUtil();
	}
	
	/**
	 * 拷贝多个文件, 方便做分页
	 */
	public static void  testCopyFiles() {
		try {
			File file = new File("/opt/hn/developTools/eclipseWKSP/lu/index/twoPageSize");
			for(File f:file.listFiles()) {
				String destFileName = FilenameUtils.getFullPath(f.getAbsolutePath())+
						FilenameUtils.getBaseName(f.getName())+".shh";
				FileUtils.copyFile(f, new File(destFileName));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查询content中含有i的记录
	 * 精确匹配
	 * 
	 * 一共查询了:6
     * 6---->jake[ff@itat.org]-->6,5,1305734400000
     * 1---->zhangsan[aa@itat.org]-->1,2,1266508800000
     * 2---->lisi[bb@itat.org]-->2,3,1326211200000
	 */
	public static void  searchByTerm() {
		su.searchByTerm("content","i",3);
	}
	
	/**
	 * 
	 */
	public static void  searchByTermRange() {
		/**
		 * 一共查询了:5
         * 2---->lisi[bb@itat.org]-->2,3,1326211200000
         * 3---->john[cc@cc.org]-->3,1,1316361600000
         * 4---->jetty[dd@sina.org]-->4,4,1292947200000
         * 5---->mike[ee@zttc.edu]-->5,5,1325347200000
         * 6---->jake[ff@itat.org]-->6,5,1305734400000
		 */
		//查询name 从a开始到s结尾,  这样的话zhangsan就不会被查询出来; 
		su.searchByTermRange("name","a","s",10);
		//由于attach是数字类型的, 使用Term是查询不出来的得使用searchByNumRange方法中有
		//su.searchByTermRange("attach","2","10", 5);
		/**
		 * 里边可以设置开区间和闭区间
		 * 
		 * 一共查询了:3
         * 1---->zhangsan[aa@itat.org]-->1,2,1266508800000
         * 2---->lisi[bb@itat.org]-->2,3,1326211200000
         * 3---->john[cc@cc.org]-->3,1,1316361600000
		 */
		//su.searchByTermRange("id","1","3", 10);
	}
	
	/**
	 * 对数字范围进行搜索
	 */
	public static void  searchByNumRange() {
		su.searchByNumricRange("attach",2,10, 5);
	}
	
	/**
	 * 前缀搜索  name 以 j开头的
	 * 
	 * 
	 * 一共查询了:3
     * 3---->john[cc@cc.org]-->3,1,1316361600000
     * 4---->jetty[dd@sina.org]-->4,4,1292947200000
     * 6---->jake[ff@itat.org]-->6,5,1305734400000
	 */
	public static void  searchByPrefix() {
		//su.searchByPrefix("name", "j", 10);
		su.searchByPrefix("content", "s", 10);
	}
	
	/**
	 * 通配符搜索
	 * 
	 * 一共查询了:3
     * 1---->zhangsan[aa@itat.org]-->1,2,1266508800000
     * 2---->lisi[bb@itat.org]-->2,3,1326211200000
     * 6---->jake[ff@itat.org]-->6,5,1305734400000
     * 一共查询了:2
     * 3---->john[cc@cc.org]-->3,1,1316361600000
     * 6---->jake[ff@itat.org]-->6,5,1305734400000
	 */
	public static void  searchByWildcard() {
		//搜索以@itat.org结尾的邮箱
		su.searchByWildcard("email", "*@itat.org", 10);
		//搜索name以j开头只面只有三位的
		su.searchByWildcard("name", "j???", 10);
	}
	
	/**
	 * BooleanQuery可以连接多个子查询
	 */
	public static void  searchByBoolean() {
		su.searchByBoolean(10);
	}
	
	/**
	 * 短语查询
	 * 只记得开头与结尾的查询
	 * 
	 * 一共查询了:2
     * 5---->mike[ee@zttc.edu]-->5,5,1325347200000
     * 4---->jetty[dd@sina.org]-->4,4,1292947200000
	 */
	public static void  searchByPhrase() {
		su.searchByPhrase(10);
	}
	
	
	/**
	 * 模糊查询
	 * 0
     * 0.4
     * 一共查询了:2
     * 5---->mike[ee@zttc.edu]-->5,5,1325347200000
     * 6---->jake[ff@itat.org]-->6,5,1305734400000
	 */
	public static void  searchByFuzzy() {
		su.searchByFuzzy(10);
	}
	
	/**
	 * football like
	 * @throws ParseException
	 */
	public static void  searchByQueryParse() throws ParseException {
		//1创建QueryParser对象,默认搜索域为content
		QueryParser parser = new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
		//改变空格默认操作符, 以下可以改成AND
		/**
		 * 一共查询了:1
         * 5---->mike[ee@zttc.edu]-->5,5,1325347200000==1.3482323
		 */
		parser.setDefaultOperator(Operator.AND);
		//开启第一个字符的通配匹配, 默认关闭, 因为效率比较底
		parser.setAllowLeadingWildcard(true);
		//直接搜索content内容有like的
		Query query = parser.parse("like");
		
		//有basketball或者football的
		query = parser.parse("basketball football");
		
		//改变搜索域name为的mike
		//query = parser.parse("name:like");
		
		//ͬ改变搜索域name以j开头的
//		query = parser.parse("name:j*");
		
		//ͨ通配符默认不能放在首位, parser.setAllowLeadingWildcard(true);进行开启
//		query = parser.parse("email:*@itat.org");
		
		//匹配name中没有mike但是content必须有football, +和-要放到域说明的前面
		//query = parser.parse("- name:mike + like");
		
		//匹配区间; 注意TO必须是大写; 匹配id从1到6, 
		//query = parser.parse("id:[1 TO 6]");
		
		//闭区间
		//query = parser.parse("id:{1 TO 3}");
		
		//匹配I Like Football是连接起来的
		//query = parser.parse("\"I like football\"");
		
		//匹配I 和football之间有一个字符的
		//query = parser.parse("\"I football\"~1");
		
		//模糊查询
		//query = parser.parse("name:make~");
		
		//数字区域匹配
		//query = parser.parse("attach:[2 TO 10]");
		su.searchByQueryParse(query, 10);
	}
	
	/**
	 * 为分页测试创建初始化索引
	 */
	public static void  indexFile() {
		FileIndexUtils.index(true);
	}
	
	/**
	 * 分页搜索
	 * 
	 */
	public static void  testSearchPage01() {
		su.searchPage("apache", 2,20);
		System.out.println("-------------------------------");
		su.searchNoPage("apache");
		//su.searchPageByAfter("java", 2,20);
	}
	
	/**
	 * 
	 */
	public static void  testSearchPage02() {
		su.searchPageByAfter("java", 3,20);
	}
	
}
