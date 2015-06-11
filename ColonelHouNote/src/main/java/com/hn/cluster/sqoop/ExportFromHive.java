package com.hn.cluster.sqoop;

import org.apache.log4j.Logger;

public class ExportFromHive
{
	public static Logger log = Logger.getLogger(ExportData.class);

	private static final String JOB_NAME = "Sqoop Hive Job";
	private static final String MAPREDUCE_JOB = "Hive Map Reduce Job";
	private static final String DBURL = "jdbc:mysql://localhost:3306/hadoop";
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";
	private static final String HADOOP_HOME = "/home/surajit/hadoop/hadoop-1.1.2";
	private static final String JAR_OUTPUT_DIR = "/home/surajit/tmp/sqoop-surajit/compile";
	private static final String HIVE_HOME = "/home/surajit/hadoop/hive-0.10.0";
	private static final String HIVE_DIR = "/user/hive/warehouse/";
	private static final String WAREHOUSE_DIR = "hdfs://localhost:9000/user/hive/warehouse";
	private static final String SUCCESS = "SUCCESS !!!";
	private static final String FAIL = "FAIL !!!";

	public void exportFromHive(String table)
	{
		log.info("Exporting data from HIVE.....");
		/* RDBMS table columns in order */
		String outCols[] = { "month", "index_name", "open", "high", "low",
				"close" };
		/* MySQL connection parameters */
		SqoopOptions options = new SqoopOptions(DBURL, table);
		options.setDriverClassName(DRIVER);
		options.setUsername(USERNAME);
		options.setPassword(PASSWORD);

		/* Hive connection parameters */
		options.setFieldsTerminatedBy(',');
		options.setHadoopMapRedHome(HADOOP_HOME);
		options.setJobName(JOB_NAME);
		options.setLinesTerminatedBy('\n');
		options.setMapreduceJobName(MAPREDUCE_JOB);
		options.setTableName(table);
		options.setJarOutputDir(JAR_OUTPUT_DIR);
		options.setClearStagingTable(true);
		options.setExportDir(HIVE_DIR + table);
		options.setDbOutputColumns(outCols);
		options.setFieldsTerminatedBy(',');
		options.setUpdateMode(SqoopOptions.UpdateMode.AllowInsert);

		ExportTool it = new ExportTool();
		int retVal = it.run(options);
		if (retVal == 0)
		{
			log.info(SUCCESS);
		} else
		{
			log.info(FAIL);
		}
	}
}
