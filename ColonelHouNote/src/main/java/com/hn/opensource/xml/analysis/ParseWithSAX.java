package com.hn.opensource.xml.analysis;

/**

 * SAX 这种处理的优点非常类似于流媒体的优点。分析能够立即开始，而不是等待所有的数据被处理。而且，由于应用程序只是在读取数据时检查数据，

 * 因此不需要将数据存储在内存中。这对于大型文档来说是个巨大的优点。事实上，应用程序甚至不必解析整个文档；它可以在某个条件得到满足时停止

 * 解析。一般来说，SAX 还比它的替代者 DOM 快许多。选择 DOM 还是选择 SAX ？ 对于需要自己编写代码来处理 XML 文档的开发人员来说，选择

 * DOM 还是 SAX 解析模型是一个非常重要的设计决策。 DOM 采用建立树形结构的方式访问 XML 文档，而 SAX 采用的事件模型。 DOM 解析器把

 * XML 文档转化为一个包含其内容的树，并可以对树进行遍历。用 DOM 解析模型的优点是编程容易，开发人员只需要调用建树的指令，然后利用

 * navigation APIs访问所需的树节点来完成任务。可以很容易的添加和修改树中的元素。然而由于使用 DOM 解析器的时候需要处理整个 XML 文档，

 * 所以对性能和内存的要求比较高，尤其是遇到很大的 XML 文件的时候。由于它的遍历能力，DOM 解析器常用于 XML 文档需要频繁的改变的服务中。

 * SAX 解析器采用了基于事件的模型，它在解析 XML 文档的时候可以触发一系列的事件，当发现给定的tag的时候，它可以激活一个回调方法，告诉该

 * 方法制定的标签已经找到。SAX 对内存的要求通常会比较低，因为它让开发人员自己来决定所要处理的tag。特别是当开发人员只需要处理文档中所包

 * 含的部分数据时，SAX 这种扩展能力得到了更好的体现。但用 SAX 解析器的时候编码工作会比较困难，而且很难同时访问同一个文档中的多处不同数据。

 */

import org.xml.sax.*;

import org.xml.sax.helpers.*;

import javax.xml.parsers.*;

/**
 * 
 * 
 * 
 * @author Administrator
 * 
 * 
 */

public class ParseWithSAX extends DefaultHandler
{

	java.util.Stack tags = new java.util.Stack();

	public ParseWithSAX()
	{

		super();

	}

	public static void main(String args[])
	{

		long lasting = System.currentTimeMillis();

		try
		{

			SAXParserFactory sf = SAXParserFactory.newInstance();

			SAXParser sp = sf.newSAXParser();

			ParseWithSAX reader = new ParseWithSAX();

			sp.parse(new InputSource(ParseWithSAX.class.getResource("test.xml")
					.getPath()), reader);

		} catch (Exception e)
		{

			e.printStackTrace();

		}

		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
				+ " 毫秒");

	}

	public void characters(char ch[], int start, int length)

	throws SAXException
	{

		String tag = (String) tags.peek();

		if (tag.equals("NO"))
		{

			System.out.print("车牌号码：" + new String(ch, start, length));

		}

		if (tag.equals("ADDR"))
		{

			System.out.println(" 地址:" + new String(ch, start, length));

		}

	}

	public void startElement(String uri, String localName, String qName,

	Attributes attrs)
	{

		tags.push(qName);

	}

}
