package com.hn.cluster.hadoop.hdfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * http://www.it165.net/admin/html/201403/2573.html
 * Hadoop版本1.2.1 系统ubuntu 12.04 JDK 1.7
 */
public class PutMerge
{

	public static void main(String[] args) throws IOException
	{
		Configuration conf = new Configuration();
		Path inputDir = new Path("/home/hadoop/input");
		String serverPath = "hdfs://localhost:9000/test";
		Path hdfsfile = new Path(serverPath);
		FileSystem hdfs = FileSystem.get(URI.create(serverPath), conf);
		// 根据上面的serverPath，获取到的是一个org.apache.hadoop.hdfs.DistributedFileSystem对象
		FileSystem local = FileSystem.getLocal(conf);
		FileStatus[] status = local.listStatus(inputDir);
		FSDataOutputStream out = hdfs.create(hdfsfile);

		for (int i = 0; i < status.length; i++)
		{
			FSDataInputStream in = local.open(status[i].getPath());
			byte buffer[] = new byte[256];
			int byteread = 0;
			while ((byteread = in.read(buffer)) > 0)
			{
				out.write(buffer);
			}
			in.close();
		}
		out.close();
	}
}