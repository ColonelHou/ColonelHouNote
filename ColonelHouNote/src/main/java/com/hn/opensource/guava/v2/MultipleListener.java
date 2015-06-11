package com.hn.opensource.guava.v2;

import com.google.common.eventbus.Subscribe;

/**
 * 只需要在要订阅消息的方法上加上@Subscribe注解即可实现对多个消息的订阅，代码如下：
 * @author John
 *
 */
public class MultipleListener
{
	public Integer lastInteger;
	public Long lastLong;

	@Subscribe
	public void listenInteger(Integer event)
	{
		lastInteger = event;
		System.out.println("event Integer:" + lastInteger);
	}

	@Subscribe
	public void listenLong(Long event)
	{
		lastLong = event;
		System.out.println("event Long:" + lastLong);
	}

	public Integer getLastInteger()
	{
		return lastInteger;
	}

	public Long getLastLong()
	{
		return lastLong;
	}
}
