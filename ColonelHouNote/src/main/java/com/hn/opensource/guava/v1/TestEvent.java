package com.hn.opensource.guava.v1;


/**
 * 消息封装类：
 * @author John
 *
 */
public class TestEvent
{
	private final int message;
    public TestEvent(int message) {        
        this.message = message;
        System.out.println("event message:"+message);
    }
    public int getMessage() {
        return message;
    }
}
