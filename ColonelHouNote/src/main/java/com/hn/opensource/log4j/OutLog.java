package com.hn.opensource.log4j;

import org.apache.log4j.Logger;

public class OutLog
{

	private static Logger log = Logger.getLogger(OutLog.class);

	public static void main(String[] args)
	{
		log.debug("my test log4j file: OutLog.main ");

		/*
		 * String str =
		 * "fdasfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffdsafadsfdsfdasfadsfadsfadsfasdfasdfasfewrqfewuorihfdkncxm,areokdsfnv,xmcr;aeiohvcxn,m loire9y8adfinklxvc449873ueriofjdslk89teriohdfkv40earopjdsf;vlm4309uwepojfsD:Lvm0uewrpofjsD:vlmxc.0dfjopvmlx;=ssc0reu9fdsoijvxlcnk .,0reodxvnklc .,0ewr9opsdfjlxvnck. ,"
		 * ;
		 * 
		 * StringBuffer buffer = new StringBuffer(); buffer.append(str);
		 * StringBuffer mid = new StringBuffer(); for (int i = 0; i < 10000;
		 * i++) { mid.append(buffer.toString()); mid.append("\n\n\n\n"); }
		 * mid.append(buffer.toString()); String s = mid.toString(); //
		 * createDate,thread,level,class,message log.error("This is mytest");
		 * for (int i = 0; i < 100000; i++) { log.debug("\n\n\n" + i +
		 * "\n\n\n"); log.debug("my test log4j file: OutLog.main " + s); }
		 */
	}
}
