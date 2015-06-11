package com.hn.cluster.sqoop;

import org.apache.log4j.Logger;

public class ImportToHive
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

	public void importToHive(String table)
	{

		System.out.println("SqoopOptions loading .....");

		/* MySQL connection parameters */
		SqoopOptions options = new SqoopOptions();
		options.setConnectString(DBURL);
		options.setTableName(table);
		options.setDriverClassName(DRIVER);
		options.setUsername(USERNAME);
		options.setPassword(PASSWORD);
		options.setHadoopMapRedHome(HADOOP_HOME);

		/* Hive connection parameters */
		options.setHiveHome(HIVE_HOME);
		options.setHiveImport(true);
		options.setHiveTableName("bsefmcgh");
		options.setOverwriteHiveTable(true);
		options.setFailIfHiveTableExists(false);
		options.setFieldsTerminatedBy(',');
		options.setOverwriteHiveTable(true);
		options.setDirectMode(true);
		options.setNumMappers(1); // No. of Mappers to be launched for the job
		options.setWarehouseDir(WAREHOUSE_DIR);
		options.setJobName(JOB_NAME);
		options.setMapreduceJobName(MAPREDUCE_JOB);
		options.setTableName(table);
		options.setJarOutputDir(JAR_OUTPUT_DIR);

		System.out.println("Import Tool running ....");
		ImportTool it = new ImportTool();
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