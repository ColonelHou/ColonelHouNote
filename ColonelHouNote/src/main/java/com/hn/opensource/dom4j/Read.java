package com.hn.opensource.dom4j;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Read
{

	public static void main(String[] args) throws IOException
	{
		Read read = new Read();
		// List<Map<String, Object>> list = read.read("student",
		// Read.class.getResource("a.txt").getPath());
		// System.out.println(list.toString());
		InputStream in = Read.class.getResourceAsStream("a.txt");
		// BufferedInputStream bis = new BufferedInputStream(in);

		//
		// FileInputStream fis = (FileInputStream)
		// Read.class.getResourceAsStream("a.txt");
		URL url = Read.class.getResource("a.txt");
		// 当前文件路经
		System.out.println("Currect File Path : "
				+ Read.class.getResource("a.txt").getPath());
		// 当前class类路经
		System.out
				.println("src path :" + Read.class.getResource("/").getPath());

		System.out.println("Pro dir : " + System.getProperty("user.dir"));
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String msg = br.readLine();
		while (msg != null)
		{
			System.out.println(msg);
			msg = br.readLine();
		}

		List<Map<String, Object>> list = read.read("student", Read.class
				.getResource("复件.xls").getPath());
		System.out.println(list.toString());
	}

	public List<Map<String, Object>> read(String sheetName, String filePath)
	{
		// 获取根结点
		Element root = getRoot(filePath);

		Element sheet = getElementByAttr(root, "Worksheet", "Name", sheetName);

		Element table = getElementByName(sheet, "Table");

		List<Map<String, Object>> list = getTableData(table, true);
		return list;
	}

	private List<Map<String, Object>> getTableData(Element table, boolean b)
	{
		List<Element> listRow = getEleListByName(table, "Row");
		List<String> listKey = new ArrayList<String>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (listRow.size() > 0)
		{
			Element ele = listRow.get(0);
			List<Element> listCell = getEleListByName(ele, "Cell");
			for (Iterator<Element> it = listCell.iterator(); it.hasNext();)
			{
				String key = it.next().getStringValue()
						.toLowerCase(Locale.getDefault());
				listKey.add(key);

			}

			Iterator<Element> remain = listRow.iterator();
			remain.next();
			while (remain.hasNext())
			{
				Map<String, Object> tmp = new HashMap<String, Object>();

				List<Element> tmpList = getEleListByName(remain.next(), "Cell");
				for (int i = 0; i < tmpList.size(); i++)
				{
					tmp.put(listKey.get(i), tmpList.get(i).getStringValue());
				}
				result.add(tmp);
			}

		}
		return result;
	}

	private List<Element> getEleListByName(Element table, String row)
	{
		List<Element> result = new ArrayList<Element>();
		Iterator<Element> list = table.elementIterator(row);
		while (list.hasNext())
		{
			result.add(list.next());
		}
		return result;
	}

	private Element getElementByName(Element sheet, String table)
	{
		Iterator<Element> it = sheet.elementIterator(table);
		while (it.hasNext())
		{
			return it.next();
		}
		return null;
	}

	private Element getElementByAttr(Element root, String Worksheet,
			String Name, String sheetName)
	{
		// root.element(Worksheet);
		Iterator<Element> it = root.elementIterator(Worksheet);

		while (it.hasNext())
		{
			Element ele = it.next();
			if (ele.attributeValue(Name).equals(sheetName))
			{
				return ele;
			}
		}
		return null;
	}

	// 获取根结点
	private Element getRoot(String filePath)
	{
		SAXReader reader = new SAXReader();
		// reader.setEncoding("UTF-8");
		reader.setEncoding("UTF-8");
		Document doc = null;
		try
		{
			doc = reader.read(filePath);
		} catch (DocumentException e)
		{
			e.printStackTrace();
		}

		return doc.getRootElement();
	}
}
