package com.hn.java.se.io;


import java.io.*;
public class 读取超内文件
{
	public static void main(String[] args) throws Exception
	{
		String filepath = "E:/test.exe";
		String outFile = "E:/copy.exe";
		long pos = firstRead(filepath, outFile);
		continueRead(filepath, outFile, pos);
	}

	/**
	 * 第一次只读取文件的一半，到目标文件
	 */
	public static long firstRead(String filepath, String out) throws Exception
	{
		RandomAccessFile file = new RandomAccessFile(filepath, "r");
		long fileLen = file.length();

		FileOutputStream outStream = new FileOutputStream(out);
		int sum = 0; // 用于记录当前读取源文件的长度
		byte[] cache = new byte[1024];
		int len = -1;
		while ((len = file.read(cache)) != -1 && sum < fileLen / 2)
		{
			outStream.write(cache, 0, len);
			sum += len;
		}
		outStream.close();
		file.close();

		return sum; // 返回当前读取源文件的长度
	}

	/**
	 * 从源文件指定位置继续读取文件内容，并输出到目标文件
	 */
	public static void continueRead(String filepath, String out, long pos)
			throws Exception
	{
		RandomAccessFile file = new RandomAccessFile(filepath, "r");
		file.seek(pos);

		// 追加到目标文件中
		FileOutputStream outStream = new FileOutputStream(out, true);
		byte[] cache = new byte[1024];
		int len = -1;
		while ((len = file.read(cache)) != -1)
		{
			outStream.write(cache, 0, len);
		}
		outStream.close();
		file.close();
	}
}
