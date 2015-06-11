package com.hn.opensource.guava.v1;
import com.google.common.eventbus.Subscribe;

/**
 * 消息接受类：
 * 
 * @author John
 * 
 */
public class EventListener
{
	public int lastMessage = 0;

	@Subscribe
	public void listen(TestEvent event)
	{
		lastMessage = event.getMessage();
		System.out.println("Message:" + lastMessage);
	}

	public int getLastMessage()
	{
		return lastMessage;
	}
}
