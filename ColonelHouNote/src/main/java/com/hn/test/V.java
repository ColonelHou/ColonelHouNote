package com.hn.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class V
{

	public static void main(String[] args)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= 54; i++)
		{
			if(i % 10 == 0)
				continue;
			sb.append("htp:" + i + ", ");
		}
		System.out.println(sb.toString());
		Map<String, String> map = map();
		Set<String> set = map.keySet();
		Set<Entry<String, String>> s = map.entrySet();
		StringBuffer strHiveField = new StringBuffer();
		int fieldNum = 0;
		for (Entry<String, String> en : s)
		{
//			System.out.println(en.getKey() + " -- > " + en.getValue());
			strHiveField.append(en.getValue() + " STRING, ");
			fieldNum ++;
		}
		System.out.println(strHiveField.toString());
		System.out.println(fieldNum);
	}
	
	
	public static Map<String, String> map()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("1","duration");             
		map.put("2","end_millis");           
		map.put("3","start_millis");         
		map.put("4","activities");           
		map.put("5","appkey");               
		map.put("6","session_id");           
		map.put("7","type");                 
		map.put("8","launch");               
		map.put("9","time");    
		
		map.put("11","terminate");           
		map.put("12","error");               
		map.put("13","context");             
		map.put("14","flush");               
		map.put("15","event");               
		map.put("16","tag");                 
		map.put("17","label");               
		map.put("18","acc");                 
		map.put("19","header");  
            
		map.put("21","body");                
		map.put("22","true");                
		map.put("23","false");               
		map.put("24","device_mac");          
		map.put("25","device_id");           
		map.put("26","device_imsi");         
		map.put("27","device_model");        
		map.put("28","device_manufacture"); 
		map.put("29","app_version");  
       
		map.put("31","version_code");        
		map.put("32","sdk_type");            
		map.put("33","sdk_version");         
		map.put("34","os");                  
		map.put("35","os_version");          
		map.put("36","country");             
		map.put("37","language");            
		map.put("38","timezone");            
		map.put("39","resolution");  
        
		map.put("41","access");              
		map.put("42","access_subtype");      
		map.put("43","carrier");             
		map.put("44","lat");                 
		map.put("45","lng");                 
		map.put("46","cpu");                 
		map.put("47","content");             
		map.put("48","unknown");             
		map.put("49","N/A");    
             
		map.put("50","attribute");           
		map.put("51","user_id");             
		map.put("52","channel");             
		map.put("53","transaction");
		map.put("54","trans_id");   
		
		return map;
	}
	
	
	
}
