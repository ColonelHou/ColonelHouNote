package com.hn.cluster.hadoop.mrs.topk;

import java.io.IOException;

/**
 * 
 * @author zx zhangxian1991@qq.com
 */
public class TopK
{
	public static void main(String args[]) throws ClassNotFoundException,
			IOException, InterruptedException
	{

		// 要统计字数，排序的文字
		String in = "hdfs://hadoopMaster:9000/input/topk/topk.txt";

		// 统计字数后的结果
		String wordCout = "hdfs://hadoopMaster:9000/out/topk";

		// 对统计完后的结果再排序后的内容
		String sort = "hdfs://hadoopMaster:9000/output/topk/sort";

		// 前K条
		String topK = "hdfs://hadoopMaster:9000/output/topk/topK";

		// 如果统计字数的job完成后就开始排序
		if (WordCount.run(in, wordCout))
		{
			Sort.run(wordCout, sort, topK);
		}

	}
}