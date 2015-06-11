package com.hn.opensource.log4j.三种输出;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main
{

	public static void main(String[] args)
	{
		sample();
	}
	
	public static void sample()
    {
        Logger logger = Logger.getLogger(Main.class);
        
        PropertyConfigurator.configure("myLog4j.properties");
        for (int i = 0; i < 1000; i++)
		{
        	logger.debug("Here is DEBUG messgae" + i);
        	logger.info("Here is INFO message" + i);
        	logger.warn("Here is WARN message" + i);
        	logger.error("Here is ERROR message" + i);
        	logger.fatal("Here is FATAL message" + i);
		}
    }
}
