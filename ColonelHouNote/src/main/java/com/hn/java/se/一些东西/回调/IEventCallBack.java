package com.hn.java.se.一些东西.回调;

/**
 * <pre>
 * 创建一个接口来作为回调函数的传递者
 * </pre>
 * 
 * @author Leoly
 * @version 1.0, 2012/3/1
 */
public interface IEventCallBack
{
	/**
	 * <pre>
	 * 需要回调的方法
	 * </pre>
	 * 
	 * @param sayWhat
	 *            String
	 */
	void call(String sayWhat);
}
