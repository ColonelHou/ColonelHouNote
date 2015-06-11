package com.hn.opensource.json;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test
{
	public static void main(String[] args)
	{
		JSONObject input = new JSONObject();
		input.put("mm", "hhh");
		JSONObject bodyjson = new JSONObject();
		bodyjson.put("xmlpath", "E:/a.xml");
		input.put("body", bodyjson.toString());
		input.toString();
		System.out.println(input.toString());

		JSONParser parser = new JSONParser();
		JSONObject jsonmsg;
		try
		{
			jsonmsg = (JSONObject) parser.parse(input.toString());
			String hdr_msg = (String) jsonmsg.get("mm");
			String hdr_body = (String) jsonmsg.get("body");
			JSONObject jsonmsg2 = (JSONObject) parser
					.parse(hdr_body.toString());
			String path = (String) jsonmsg2.get("amlpath");
			System.out.println(hdr_msg + ", " + hdr_body + ", " + path);

		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}
