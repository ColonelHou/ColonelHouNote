package com.hn.cluster.redis;

import redis.clients.jedis.Jedis;

public class Test
{
	public static void main(String[] args)
	{
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("foo", "bar");
		String value = jedis.get("foo");
		System.out.println(value);
	}
}
