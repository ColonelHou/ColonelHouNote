package com.hn.opensource.log4j.tools.配置多个输出文件;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestObservable
{

	static Logger chargeLogger = LoggerFactory.getLogger("charge_log");
	static Logger goldLogger = LoggerFactory.getLogger("gold_log");
	static Logger propLogger = LoggerFactory.getLogger("prop_log");
	static Logger registerLogger = LoggerFactory.getLogger("register_log");
	static Logger activeLogger = LoggerFactory.getLogger("active_log");
	static Logger gamePlayLogger = LoggerFactory.getLogger("game_play_log");
	static Logger onlineLogger = LoggerFactory.getLogger("online_log");


	public static void main(String[] args)
	{
		
		chargeLogger.info("chargeLogger");
		activeLogger.info("activeLogger");
		gamePlayLogger.info("gamePlayLogger");
		goldLogger.info("goldLogger");
		onlineLogger.info("onlineLogger");
		propLogger.info("propLogger");
		registerLogger.info("registerLogger");
	}
}