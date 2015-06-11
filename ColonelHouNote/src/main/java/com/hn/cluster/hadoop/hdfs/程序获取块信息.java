package com.hn.cluster.hadoop.hdfs;

public class 程序获取块信息
{
	public static void getFileLocal() throws IOException
	{
		Configuration conf = new Configuration();
		FileSystem fs = FileSystem.get(conf);
		Path path = new Path("/user/hadoop1/input/10000");
		FileStatus status = fs.getFileStatus(path);
		BlockLocation[] locations = fs.getFileBlockLocations(status, 0,
				status.getLen());
		int length = locations.length;
		System.out.println("total locations = " + length);
		for (int i = 0; i < length; i++)
		{
			String[] ss1 = locations[i].getNames();
			String[] ss2 = locations[i].getHosts();
			System.out.println("------names-------");
			for (String s : ss1)
			{
				System.out.print(s + ",");
			}
			System.out.println();
			System.out.println("------hosts-------");
			for (String s : ss2)
			{
				System.out.print(s + ",");
			}
		}
	}
}
