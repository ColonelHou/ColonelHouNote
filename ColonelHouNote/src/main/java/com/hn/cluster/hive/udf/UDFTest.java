package com.hn.cluster.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

public class UDFTest extends UDF {

	public boolean evaluate(Text t1, Text t2){
		if(t1 == null || t2 == null)
		{
			return false;
		}
		
		double d1 = Double.parseDouble(t1.toString());
		double d2 = Double.parseDouble(t2.toString());
		
		if(d1 >d2){
			return true;
		}else{
			return false;
		}
	}
}
