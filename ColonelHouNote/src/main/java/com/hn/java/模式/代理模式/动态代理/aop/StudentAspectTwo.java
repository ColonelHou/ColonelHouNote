package com.hn.java.模式.代理模式.动态代理.aop;

public class StudentAspectTwo implements Aspect{  
    @Override  
    public void doAfter() {  
        System.out.println("do After Two");  
          
    }  
  
    @Override  
    public void doBefore() {  
        System.out.println("do Before Two");  
          
    }  
}  
