package com.hn.java.se.thread.Executors;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;


/**
 * Flume配置文件自动加载
 * 配置使用Google的guava来做的
 * @author Colonel.Hou
 *
 */
public class ScheduledExecutorServiceTest {

	private ScheduledExecutorService executorService;

	public void check(String strFile)
	{
		executorService = Executors
				.newSingleThreadScheduledExecutor(new ThreadFactoryBuilder()
						.setNameFormat("conf-file-poller-%d").build());
		File file = new File(strFile);
		ScheduledExecutorServiceTest.FileWatcherRunnable fileWatcherRunnable = new FileWatcherRunnable(file);
		executorService.scheduleWithFixedDelay(fileWatcherRunnable, 0, 10,
				TimeUnit.SECONDS);
	}
	public static void main(String[] args) {
		String strFile = ScheduledExecutorServiceTest.class.getResource("").getPath() + "flume.properties";
		ScheduledExecutorServiceTest sst = new ScheduledExecutorServiceTest();
		sst.check(strFile);
	}

	public class FileWatcherRunnable implements Runnable {

		private final File file;

		private long lastChange;

		public FileWatcherRunnable(File file) {
			this.file = file;
			this.lastChange = 0L;
		}

		@Override
		public void run() {

			long lastModified = file.lastModified();

			if (lastModified > lastChange) {

				System.out.println("文件被修改过..");
				lastChange = lastModified;

			}else{
				System.out.println("文件未修改过..");
			}
			
		}
	}
}
