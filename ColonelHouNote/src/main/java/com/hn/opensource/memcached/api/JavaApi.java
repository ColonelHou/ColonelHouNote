package com.hn.opensource.memcached.api;

import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.schooner.MemCached.MemcachedItem;

public class JavaApi
{
	public static void init()
	{
		String[] servers =
		{ "192.168.13.17:11211" };
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers(servers);// 设置服务器
		pool.setFailover(true);// 容错
		pool.setInitConn(10);// 设置初始连接数
		pool.setMinConn(5);// 设置最小连接数
		pool.setMaxConn(25); // 设置最大连接数
		pool.setMaintSleep(30);// 设置连接池维护线程的睡眠时间
		pool.setNagle(false);// 设置是否使用Nagle 算法
		pool.setSocketTO(3000);// 设置socket 的读取等待超时时间
		pool.setAliveCheck(true);// 设置连接心跳监测开关
		pool.setHashingAlg(SockIOPool.CONSISTENT_HASH);// 设置Hash 算法
		pool.initialize();
	}
	
	public static void main(String[] args)
	{
		init();
		MemCachedClient memCachedClient = new MemCachedClient();
		添加值替换(memCachedClient);
		
		获取指定一组key(memCachedClient);
		
		指定Key的值添加前缀或者后缀(memCachedClient);
		
		获取指定Key的值及版本(memCachedClient);
	}

	private static void 获取指定Key的值及版本(MemCachedClient memCachedClient)
	{
		MemcachedItem item = memCachedClient.gets("key");
		memCachedClient.cas("key", (Integer)item.getValue() + 1,
		item.getCasUnique());
		System.out.println(item.getValue().toString());//3
		System.out.println(item.getCasUnique());//69
	}

	private static void 获取指定一组key(MemCachedClient memCachedClient)
	{
		String[] keys = {"key","key1"};
		//获取一组key值
		Map<String, Object> values = memCachedClient.getMulti(keys);
		System.out.println(values.toString());//{}
	}

	private static void 添加值替换(MemCachedClient memCachedClient)
	{
		memCachedClient.add("key", 1);
		memCachedClient.set("key", 2);
		memCachedClient.replace("key", 3);
		memCachedClient.set("key1", 78);
		Object value = memCachedClient.get("key");
		System.out.println(value.toString());//3
		memCachedClient.incr("key", 1);
		System.out.println(value.toString());
		memCachedClient.decr("key",1);
		System.out.println(value.toString());
	}

	/**
	 * 给指定的key加前缀或者后缀
	 * @param memCachedClient
	 */
	private static void 指定Key的值添加前缀或者后缀(MemCachedClient memCachedClient)
	{
		memCachedClient.set("kName", "ColonelHou");
		memCachedClient.prepend("kName", "hello");
		//hellochenkangxian
		System.out.println(memCachedClient.get("kName"));
		memCachedClient.append("kName", "!");
		//hellochenkangxian!
		System.out.println(memCachedClient.get("kName"));
	}
}
