package com.hn.cluster.sqoop;

import org.apache.log4j.Logger;

public class ImportToHbase
{
	public static Logger log = Logger.getLogger(ExportData.class);

	private static final String JOB_NAME = "Sqoop HBase Job";
	private static final String MAPREDUCE_JOB = "HBase Map Reduce Job";
	private static final String DBURL = "jdbc:mysql://localhost:3306/hadoop";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String HADOOP_HOME = "/home/surajit/hadoop/hadoop-1.1.2";
	private static final String JAR_OUTPUT_DIR = "/home/surajit/tmp/sqoop-surajit/compile";
	private static final String SUCCESS = "SUCCESS !!!";
	private static final String FAIL = "FAIL !!!";

	public void importToHbase(String table)
	{
		int retVal = 0;
		SqoopOptions options = new SqoopOptions(DBURL, table);
		Options.setDriverClassName(DRIVER);
		options.setUsername(USERNAME);
		options.setPassword(PASSWORD);
		options.setHadoopMapRedHome(HADOOP_HOME);
		options.setHBaseTable(table);
		options.setHBaseRowKeyColumn("date");
		options.setHBaseColFamily("firstdecade");
		options.setJobName(JOB_NAME);
		options.setMapreduceJobName(MAPREDUCE_JOB);
		options.setTableName(table);
		options.setJarOutputDir(JAR_OUTPUT_DIR);
		options.setCreateHBaseTable(true);
		options.setDirectMode(true);

		ImportTool it = new ImportTool();

		retVal = it.run(options);
		if (retVal == 0)
		{
			log.info(SUCCESS);
		} else
		{
			log.info(FAIL);
		}
	}
}
