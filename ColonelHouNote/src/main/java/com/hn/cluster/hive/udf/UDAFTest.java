package com.hn.cluster.hive.udf;

import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;


/**
 * 统计大于30的个数
 * @author John
 *
 */
public class UDAFTest extends AbstractGenericUDAFResolver {

	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] info)
			throws SemanticException {
		//做类型检查
		
		
		return super.getEvaluator(info);
	}

	public static class genericEvaluate extends GenericUDAFEvaluator
	{

		
		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters)
				throws HiveException {
			
			
			return super.init(m, parameters);
		}

		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			
			
			//保存缓存结果
			return null;
		}

		@Override
		public void iterate(AggregationBuffer arg0, Object[] arg1)
				throws HiveException {
			// TODO Auto-generated method stub
			
			//读取每条记录
		}

		@Override
		public void merge(AggregationBuffer arg0, Object arg1)
				throws HiveException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reset(AggregationBuffer arg0) throws HiveException {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public Object terminate(AggregationBuffer arg0) throws HiveException {
			// TODO Auto-generated method stub
			//最终返回的结果
			return null;
		}

		@Override
		public Object terminatePartial(AggregationBuffer arg0)
				throws HiveException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
