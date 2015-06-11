package com.hn.opensource.log4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogMoreFile {

	private static Logger log = Logger.getLogger("LogMoreFile");

	public static void main(String[] args) {

		try {
			Properties p = new Properties();
			p.setProperty("catalina.home", "E:\\hn\\tmp\\log4j\\haha");
			p.setProperty("logFile.Name", "haha.log");
			p.load(new FileInputStream(System.getProperty("user.dir")
					+ "/src/log4j.properties"));
			
			for (int j = 0; j < 30; j++) {
				if (j % 2 == 0) {
					p.setProperty("catalina.home", "E:\\hn\\tmp\\log4j\\2222");
					p.setProperty("logFile.Name", "2222.log");
					PropertyConfigurator.configure(p);
					log.error("Hello World" + j);
				} else if (j % 3 == 0) {
					p.setProperty("catalina.home", "E:\\hn\\tmp\\log4j\\3333");
					p.setProperty("logFile.Name", "haha.log");
					PropertyConfigurator.configure(p);
					log.error("Hello World " + j);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
