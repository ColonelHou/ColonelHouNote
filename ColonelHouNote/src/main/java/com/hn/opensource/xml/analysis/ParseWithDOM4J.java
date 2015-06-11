package com.hn.opensource.xml.analysis;

/**

 * DOM4J http://dom4j.sourceforge.net/

 虽然 DOM4J 代表了完全独立的开发结果，但最初，它是 JDOM 的一种智能分支。它合并了许多超出基本 XML 文档表示的功能，

 包括集成的 XPath 支持、XML Schema 支持以及用于大文档或流化文档的基于事件的处理。它还提供了构建文档表示的选项，它

 通过 DOM4J API 和标准 DOM 接口具有并行访问功能。从 2000 下半年开始，它就一直处于开发之中。

 为支持所有这些功能，DOM4J 使用接口和抽象基本类方法。DOM4J 大量使用了 API 中的 Collections 类，但是在许多情况下，

 它还提供一些替代方法以允许更好的性能或更直接的编码方法。直接好处是，虽然 DOM4J 付出了更复杂的 API 的代价，但是它提供了

 比 JDOM 大得多的灵活性。

 在添加灵活性、XPath 集成和对大文档处理的目标时，DOM4J 的目标与 JDOM 是一样的：针对 Java 开发者的易用性和直观操作。

 它还致力于成为比 JDOM 更完整的解决方案，实现在本质上处理所有 Java/XML 问题的目标。在完成该目标时，它比 JDOM 更少

 强调防止不正确的应用程序行为。

 DOM4J 是一个非常非常优秀的Java XML API，具有性能优异、功能强大和极端易用使用的特点，同时它也是一个开放源代码的软件。

 如今你可以看到越来越多的 Java 软件都在使用 DOM4J 来读写 XML，特别值得一提的是连 Sun 的 JAXM 也在用 DOM4J。

 DOM 和 DOM 在性能测试时表现不佳，在测试 10M 文档时内存溢出。在小文档情况下还值得考虑使用 DOM 和 JDOM。虽然 JDOM

 的开发者已经说明他们期望在正式发行版前专注性能问题，但是从性能观点来看，它确实没有值得推荐之处。另外，DOM 仍是一个非常

 好的选择。DOM 实现广泛应用于多种编程语言。它还是许多其它与 XML 相关的标准的基础，因为它正式获得 W3C

 推荐（与基于非标准的 Java 模型相对），所以在某些类型的项目中可能也需要它（如在 JavaScript 中使用 DOM）。

 SAX表现较好，这要依赖于它特定的解析方式。一个 SAX 检测即将到来的XML流，但并没有载入到内存（当然当XML流被读入时，

 会有部分文档暂时隐藏在内存中）。

 无疑，DOM4J是这场测试的获胜者，目前许多开源项目中大量采用 DOM4J，例如大名鼎鼎的 Hibernate 也用 DOM4J 来读取

 XML 配置文件。如果不考虑可移植性，那就采用DOM4J吧！

 */

import java.io.File;

import java.util.Iterator;

import org.dom4j.Document;

import org.dom4j.Element;

import org.dom4j.io.SAXReader;

/**
 * 
 * 
 * 
 * @author Administrator
 * 
 * 
 */

public class ParseWithDOM4J
{

	public static void main(String arge[])
	{

		long lasting = System.currentTimeMillis();

		try
		{

			File f = new File(ParseWithDOM4J.class.getResource("test.xml")
					.getPath());

			SAXReader reader = new SAXReader();

			Document doc = reader.read(f);

			Element root = doc.getRootElement();

			Element foo;

			for (Iterator i = root.elementIterator("VALUE"); i.hasNext();)
			{

				foo = (Element) i.next();

				System.out.print("车牌号码:" + foo.elementText("NO"));

				System.out.println(" 车主地址:" + foo.elementText("ADDR"));

			}

		} catch (Exception e)
		{

			e.printStackTrace();

		}

		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
				+ " 毫秒");

	}
}
