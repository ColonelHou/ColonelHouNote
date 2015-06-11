package com.hn.java.se.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import sun.misc.*;

public class Base64 {
	// 加密
	public static String getBase64(String str) {
		byte[] b = null;
		String s = null;
		try {
			b = str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	// 解密
	public static String getFromBase64(String s) {
		byte[] b = null;
		String result = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				result = new String(b);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws IOException {
		
		
		String path = Base64.class.getResource(".").getPath(); path = path +
		"a.txt"; BufferedReader br = new BufferedReader(new
		InputStreamReader(new FileInputStream(new File(path))));
		
		StringBuffer sb = new StringBuffer();
		String str = br.readLine(); 
		sb.append("NOT IN (");
		while(str != null){
			sb.append("'" + str + "', ");
			//System.out.println(getFromBase64(str)); 
			str = br.readLine();
		}
		sb.append(")");
		System.out.println(sb.toString());
		br.close();
		 
		// System.out.println(getFromBase64(s));

		
	}
}
