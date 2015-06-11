package com.hn.cluster.hadoop.mrUnit.v1;

import junit.framework.TestCase;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.lib.IdentityMapper;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.mrunit.MapDriver;

public class TestExample extends TestCase
{

	private Mapper<Text, Text, Text, Text> mapper;
	private MapDriver<Text, Text, Text, Text> driver;

	@Before
	public void setUp()
	{
		mapper = new IdentityMapper<Text, Text>();
		driver = new MapDriver<Text, Text, Text, Text>(mapper);
	}

	@Test
	public void testIdentityMapper()
	{
		driver.withInput(new Text("foo"), new Text("bar"))
				.withOutput(new Text("foo"), new Text("bar")).runTest();
	}
}
