package com.hn.java.模式.代理模式.动态代理.aop;


import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * 代理工厂类
 */
public class DynamicProxyFactory {
	
	/**
	 * 私有构造方法
	 */
	private DynamicProxyFactory() {}
	
	/**
	 * 工厂方法
	 * 
	 * @param instance 代理目标类实例对象
	 * @param aspect 切面对象
	 */
	public static Object newInstance(Object instance, Aspect aspect) {
		
		List<Aspect> aspectList = new ArrayList<Aspect>();
		aspectList.add(aspect);
		
		return newInstance(instance, aspectList);
	}
	
	/**
	 * 工厂方法
	 * 
	 * @param instance 代理目标类实例对象
	 * @param aspectList 切面集合
	 */
	public static Object newInstance(Object instance, List<Aspect> aspectList) {
		SimpleInvocationHandler hander = new SimpleInvocationHandler();
		hander.setAspectList(aspectList);
		hander.setSource(instance);
		return Proxy.newProxyInstance(instance.getClass().getClassLoader(), 
									  instance.getClass().getInterfaces(), 
									  hander);
	}
}
