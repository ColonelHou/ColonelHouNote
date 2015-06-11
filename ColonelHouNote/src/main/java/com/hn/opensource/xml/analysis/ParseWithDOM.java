package com.hn.opensource.xml.analysis;

import java.io.*;

import org.w3c.dom.*;

import javax.xml.parsers.*;

/**
 * 
 * DOM（JAXP Crimson 解析器） DOM 是用与平台和语言无关的方式表示 XML 文档的官方 W3C 标准。
 * 
 * DOM 是以层次结构组织的节点或信息片断的集合。这个层次结构允许开发人员在树中寻找特定信息。分析该结构通常需要
 * 
 * 加载整个文档和构造层次结构，然后才能做任何工作。由于它是基于信息层次的，因而 DOM 被认为是基于树或基于对象的。
 * 
 * DOM 以及广义的基于树的处理具有几个优点。首先，由于树在内存中是持久的，因此可以修改它以便应用程序能对数据和
 * 
 * 结构作出更改。它还可以在任何时候在树中上下导航，而不是像 SAX 那样是一次性的处理。DOM 使用起来也要简单得多。
 * 
 * 另一方面，对于特别大的文档，解析和加载整个文档可能很慢且很耗资源，因此使用其他手段来处理这样的数据会更好。这
 * 
 * 些基于事件的模型，比如 SAX。
 */

public class ParseWithDOM
{

	public static void main(String arge[])
	{

		long lasting = System.currentTimeMillis();

		try
		{
			File f = new File(ParseWithDOM.class.getResource("test.xml")
					.getPath());

			DocumentBuilderFactory factory = DocumentBuilderFactory

			.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = builder.parse(f);

			NodeList node = doc.getElementsByTagName("VALUE");

			for (int i = 0; i < node.getLength(); i++)
			{

				System.out.print("车牌号码:"
						+ doc.getElementsByTagName("NO").item(i)

						.getFirstChild().getNodeValue());

				System.out.println(" 车主地址:"
						+ doc.getElementsByTagName("ADDR").item(i)

						.getFirstChild().getNodeValue());

			}

		} catch (Exception e)
		{

			e.printStackTrace();

		}

		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
				+ " 毫秒");

	}
}
