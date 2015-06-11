package com.hn.java.se.一些东西.定时器;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 隔3秒钟从一个文件中把内容读出来并打印到控制台
 * 
 * @author hadoop
 * 
 */
public class TimerTaskTest
{

	private Timer timer;

	public TimerTaskTest()
	{
		timer = new Timer();
	}

	private TimerTask task = new TimerTask()
	{
		public void run()
		{
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(
						"/opt/software/Develop/JAVA/xxx.txt"));
				String data = null;
				while ((data = br.readLine()) != null)
				{
					System.out.println(data);
				}
				System.out.println("-------------------------------------");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	};

	public void start(int delay, int internal)
	{
		timer.schedule(task, 0, internal * 1000);
	}

	public static void main(String[] args)
	{
		TimerTaskTest pt = new TimerTaskTest();
		pt.start(1, 2);
	}
}
