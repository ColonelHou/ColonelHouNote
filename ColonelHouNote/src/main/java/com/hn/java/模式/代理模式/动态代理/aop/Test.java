package com.hn.java.模式.代理模式.动态代理.aop;

import java.util.ArrayList;
import java.util.List;

public class Test {  
	  
    public static void main(String[] args) {  
          
        List<Aspect> aspectList = new ArrayList<Aspect>();  
          
        aspectList.add(new StudentAspectOne());  
        aspectList.add(new StudentAspectTwo());  
        Student s = (Student)DynamicProxyFactory.newInstance(new StudentImpl(), aspectList);  
        s.sayHello();  
    }  
  
}  
