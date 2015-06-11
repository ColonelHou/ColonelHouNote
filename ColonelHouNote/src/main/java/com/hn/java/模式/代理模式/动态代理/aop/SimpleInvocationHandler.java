package com.hn.java.模式.代理模式.动态代理.aop;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 代理委托接口实现
 */
public class SimpleInvocationHandler implements InvocationHandler {

	
	private Object source = null;
	
	private List<Aspect> aspectList = null;
	
	
	public Object getSource() {
		return source;
	}


	public void setSource(Object source) {
		this.source = source;
	}


	public List<Aspect> getAspectList() {
		return aspectList;
	}


	public void setAspectList(List<Aspect> aspectList) {
		this.aspectList = aspectList;
	}

	/**
	 * 委托方法
	 * 
	 * @param proxy 代理对象
	 * @param method 代理方法
	 * @param args 方法参数
	 */
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		for (Aspect aspect : aspectList) {
			aspect.doBefore();
		}
		
		Object retObj = method.invoke(getSource(), args);
		
		for (int index = aspectList.size() - 1; index >= 0; index--) {
			aspectList.get(index).doAfter();
		}
		
		return retObj;
	}
	
}