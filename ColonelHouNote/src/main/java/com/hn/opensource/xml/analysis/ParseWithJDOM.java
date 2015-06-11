package com.hn.opensource.xml.analysis;

/**

 * JDOM http://www.jdom.org/

 JDOM 的目的是成为 Java 特定文档模型，它简化与 XML 的交互并且比使用 DOM 实现更快。由于是第一个 Java 特定模型，JDOM 一直得到大力推广

 和促进。正在考虑通过“Java 规范请求 JSR-102”将它最终用作“Java 标准扩展”。从 2000 年初就已经开始了 JDOM 开发。

 JDOM 与 DOM 主要有两方面不同。首先，JDOM 仅使用具体类而不使用接口。这在某些方面简化了 API，但是也限制了灵活性。

 第二，API 大量使用了 Collections 类，简化了那些已经熟悉这些类的 Java 开发者的使用。

 JDOM 文档声明其目的是“使用 20%（或更少）的精力解决 80%（或更多）Java/XML 问题”（根据学习曲线假定为 20%）。

 JDOM 对于大多数 Java/XML 应用程序来说当然是有用的，并且大多数开发者发现 API 比 DOM 容易理解得多。JDOM 还包括对程序行为的相当广

 泛检查以防止用户做任何在 XML 中无意义的事。然而，它仍需要您充分理解 XML 以便做一些超出基本的工作（或者甚至理解某些情况下的错误）。

 这也许是比学习 DOM 或 JDOM 接口都更有意义的工作。

 JDOM 自身不包含解析器。它通常使用 SAX2 解析器来解析和验证输入 XML 文档（尽管它还可以将以前构造的 DOM 表示作为输入）。

 它包含一些转换器以将 JDOM 表示输出成 SAX2 事件流、DOM 模型或 XML 文本文档。JDOM 是在 Apache 许可证变体下发布的开放源码。

 */

import java.io.File;

import java.util.List;

import org.jdom.Document;

import org.jdom.Element;

import org.jdom.input.SAXBuilder;

/**
 * 
 * 
 * 
 * @author Administrator
 * 
 * 
 */

public class ParseWithJDOM
{

	public static void main(String arge[])
	{

		long lasting = System.currentTimeMillis();

		try
		{

			SAXBuilder builder = new SAXBuilder();

			Document doc = builder.build(new File("test.xml"));

			Element foo = doc.getRootElement();

			List allChildren = foo.getChildren();

			for (int i = 0; i < allChildren.size(); i++)
			{

				System.out.print("车牌号码:" +

				((Element) allChildren.get(i)).getChild("NO")

				.getText());

				System.out.println(" 车主地址:"
						+ ((Element) allChildren.get(i)).getChild("ADDR")

						.getText());

			}

		} catch (Exception e)
		{

			e.printStackTrace();

		}

		System.out.println("运行时间：" + (System.currentTimeMillis() - lasting)
				+ " 毫秒");

	}
}
