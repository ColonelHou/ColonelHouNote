package com.hn.cluster.hive.udf;

import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.parse.SemanticException;
import org.apache.hadoop.hive.ql.udf.generic.AbstractGenericUDAFResolver;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDAFEvaluator.Mode;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorUtils;
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;
import org.apache.hadoop.io.LongWritable;

/**
 * 大于30的有几个
 * hive自带的GenericUDAFCount
 * 
 * @author
 * 
 */
public class CountBigThan extends AbstractGenericUDAFResolver {

	@Override
	public GenericUDAFEvaluator getEvaluator(TypeInfo[] parameters)
			throws SemanticException {
		if (parameters.length != 2) {
			throw new UDFArgumentTypeException(parameters.length - 1,
					"Exactly two argument is expected.");
		}

		return new GenericUDAFCountBigThanEvaluator();
	}

	public static class GenericUDAFCountBigThanEvaluator extends
			GenericUDAFEvaluator {

		private LongWritable result;

		private PrimitiveObjectInspector inputOI1;
		private PrimitiveObjectInspector inputOI2;

		/**
		 * map阶段, parameters长度与udaf输入的参数个数有关 reduce阶段,parameters长度为1
		 */
		@Override
		public ObjectInspector init(Mode m, ObjectInspector[] parameters)
				throws HiveException {
			result = new LongWritable(0);
			inputOI1 = (PrimitiveObjectInspector) parameters[0];
			if (parameters.length > 1) {
				inputOI2 = (PrimitiveObjectInspector) parameters[1];
			}
			return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
		}

		@Override
		public AggregationBuffer getNewAggregationBuffer() throws HiveException {
			CountAgg agg = new CountAgg();
			reset(agg);
			return agg;
		}

		@Override
		public void iterate(AggregationBuffer agg, Object[] par)
				throws HiveException {
			// TODO Auto-generated method stub
			if(par == null || par[0] == null || par[1] == null){
				return;
			}
			double base = PrimitiveObjectInspectorUtils.getDouble(par[0], inputOI1);
			double tmp = PrimitiveObjectInspectorUtils.getDouble(par[1], inputOI2);
			
			if(base > tmp){
				((CountAgg)agg).count ++;
			}
			
		}

		@Override
		public void merge(AggregationBuffer agg, Object partial)
				throws HiveException {
			if(partial != null){
				long p = PrimitiveObjectInspectorUtils.getLong(partial, inputOI1);
				((CountAgg)agg).count += p;
			}
		}

		@Override
		public void reset(AggregationBuffer countAgg) throws HiveException {

			CountAgg agg = (CountAgg) countAgg;
			agg.count = 0;
		}

		@Override
		public Object terminate(AggregationBuffer agg) throws HiveException {
			result.set(((CountAgg)agg).count);
			return result;
		}

		@Override
		public Object terminatePartial(AggregationBuffer agg)
				throws HiveException {
			result.set(((CountAgg)agg).count);
			return result;
		}
		
		public static class CountAgg implements AggregationBuffer
		{
			long count;
		}

	}
}
