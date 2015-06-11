package com.hn.java.se;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

public class JSON与集合互转
{
	/*
	 * 
	 * /** json字符串 转成 map
	 * 
	 * @param jsonStr
	 * 
	 * @return
	 * 
	 * @throws Exception
	 * 
	 * public static HashMap<String, JsonValue> parse(String jsonStr) { if
	 * (jsonStr == null || "".equals(jsonStr)) { return null; } HashMap<String,
	 * JsonValue> retMap = null; try { retMap = new HashMap<String,
	 * JsonValue>(); JSONObject json = JSONObject.fromObject(jsonStr);
	 * Map<String, Object> tmpMap = (Map<String, Object>) JSONObject
	 * .toBean(json, Map.class); for (Map.Entry<String, Object> entry :
	 * tmpMap.entrySet()) { JsonValue tmp = parseRec(entry.getValue(), 0);
	 * retMap.put(entry.getKey(), tmp); } } catch (Exception e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return retMap; }
	 *//**
	 * json字符串 转成 List
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static List<HashMap<String, JsonValue>> parseList(String jsonStr)
	 * { if (jsonStr == null || "".equals(jsonStr)) { return null; }
	 * 
	 * List<HashMap<String, JsonValue>> retList = new ArrayList<HashMap<String,
	 * JsonValue>>();
	 * 
	 * JSONArray data = JSONArray.fromObject(jsonStr); for (int i = 0; i <
	 * data.size(); i++) { HashMap<String, JsonValue> retMap = new
	 * HashMap<String, JsonValue>();
	 * 
	 * JSONObject json = (JSONObject) data.get(i); Map<String, Object> tmpMap =
	 * (Map<String, Object>) JSONObject .toBean(json, Map.class);
	 * 
	 * for (Map.Entry<String, Object> entry : tmpMap.entrySet()) { JsonValue tmp
	 * = parseRec(entry.getValue(), 0); retMap.put(entry.getKey(), tmp); }
	 * retList.add(retMap); } return retList; }
	 *//**
	 * HashMap<String, JsonValue> map 转成 json字符串
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static String parse(HashMap<String, JsonValue> map) {
	 * HashMap<String, Object> retMap = new HashMap<String, Object>(); for
	 * (Map.Entry<String, JsonValue> entry : map.entrySet()) { Object tmp =
	 * parseJsonValueRec(entry.getValue(), 0); retMap.put(entry.getKey(), tmp);
	 * } JsonConfig jc = new JsonConfig(); return JSONObject.fromObject(retMap,
	 * jc).toString(); }
	 *//**
	 * List<HashMap<String, JsonValue>> list 转成 json字符串
	 * 
	 * @param jsonStr
	 * @return
	 * @throws Exception
	 */
	/*
	 * public static String parse(List<HashMap<String, JsonValue>> list) {
	 * List<HashMap<String, Object>> tmpList = new ArrayList<HashMap<String,
	 * Object>>();
	 * 
	 * for (HashMap<String, JsonValue> map : list) { HashMap<String, Object>
	 * retMap = new HashMap<String, Object>(); for (Map.Entry<String, JsonValue>
	 * entry : map.entrySet()) { Object tmp =
	 * parseJsonValueRec(entry.getValue(), 0); retMap.put(entry.getKey(), tmp);
	 * } tmpList.add(retMap); } JSONArray json = new JSONArray();
	 * json.addAll(tmpList); return json.toString(); }
	 *//**
	 * 构建json
	 * 
	 * @param map
	 * @return
	 */
	/*
	 * public static String parse(Map map) { JsonConfig jc = new JsonConfig();
	 * return JSONObject.fromObject(map, jc).toString(); }
	 */
}
