package com.hn.java.模式.代理模式.动态代理.aop;

public class StudentAspectOne implements Aspect {  
	  
    @Override  
    public void doAfter() {  
        System.out.println("do After One");  
          
    }  
  
    @Override  
    public void doBefore() {  
        System.out.println("do Before One");  
          
    }  
  
} 