package com.hn.cluster.hadoop.mrs.本地文件合并到HDFS;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.hn.java.se.properties.getFromPro.GetIPfromProperties;

public class PutMerge
{

	public static void main(String[] args) throws IOException
	{
		String strIP = GetIPfromProperties.getIpsFromProperties().get(
				"outputIp");
		System.out.println(strIP);
		String strPaths[] = new String[] { "/opt/hadoop/testData/putmerge",
				"hdfs://" + strIP + ":9000/output/putmerge/putMerge.txt" };
		Configuration conf = new Configuration();
		FileSystem local = FileSystem.getLocal(conf);

		Path inputDir = new Path(strPaths[0]);
		Path hdfsDir = new Path(strPaths[1]);

		FileStatus[] inputFiles = local.listStatus(inputDir);
		FileSystem hdfsFS = hdfsDir.getFileSystem(conf);
		FSDataOutputStream out = hdfsFS.create(hdfsDir);

		for (int i = 0; i < inputFiles.length; i++)
		{
			System.out.println(inputFiles[i].getPath().getName());
			FSDataInputStream in = local.open(inputFiles[i].getPath());
			byte buffer[] = new byte[256];
			int bytesRead = 0;
			while ((bytesRead = in.read(buffer)) > 0)
			{
				out.write(buffer, 0, bytesRead);
			}
			in.close();
		}

		out.close();

	}
}
