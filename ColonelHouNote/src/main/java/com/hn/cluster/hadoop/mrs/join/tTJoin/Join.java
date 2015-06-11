package com.hn.cluster.hadoop.mrs.join.tTJoin;


/**
 * info.txt文件
 * 1003 kaka
 * 1004 da
 * 1005 jue
 * 1006 zhao
 * ===============================
 * cpdata.txt文件
 * 201001 1003 abc
 * 201002 1005 def
 * 201003 1006 ghi
 * 201004 1003 jkl
 * 201005 1004 mno
 * 201006 1005 pqr
 * 201001 1003 abc
 * 201004 1003 jkl
 * 201006 1005 mno
 * 200113 1007 zkl
 * ====================生成文件：
 * 1003	201001 abc kaka
 * 1003	201004 jkl kaka
 * 1004	201005 mno da
 * 1005	201002 def jue
 * 1005	201006 pqr jue
 * 1005	201006 mno jue
 * 1006	201003 ghi zhao
 * @author 123
 *
 */
public class Join extends Configured implements Tool {

	public static class AdMap extends Mapper<LongWritable, Text, Text, TextPair>{

		@Override
		protected void map(LongWritable key, Text value, Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
//		 	String filePath = ((FileSplit)context.getInputSplit()).getPath().toString();
			Text word = new Text();
		 	
		 	String line = value.toString();
		 	String[] childline = line.split(" ");    //以空格截取
			       //判断是哪一张表,其实个人觉得这样判断还不合理,可以使用上面注视掉的获取路径值来判断
                               if(childline.length == 3){
					 TextPair pair = new TextPair();
					 pair.setFlag("0");         //这是个标识   0.表示 cpdata.txt     1表示info.txt
					 pair.setKey(childline[1]);
					 pair.setValue(childline[0]+" "+childline[2]);
					 pair.setContent(pair.toString());
					 word.clear();
					 word.set(pair.getKey());
                                        &nbsp;context.write(word, pair);        //传递一个对象要实现WritableComparable接口
				}else{
					TextPair pair = new  TextPair();
					pair.setFlag("1");
					pair.setKey(childline[0]);
					pair.setValue(childline[1]);
					pair.setContent(pair.toString());
					word.clear();
					word.set(pair.getKey());
					context.write(word, pair);
				}
			
			
		}
		
	}
	
	public static class AdReduce extends Reducer<Text, TextPair, Text, Text>{

		@Override
		public void reduce(Text key, Iterable<TextPair> values,
				Context context)
				throws IOException, InterruptedException {
			// TODO Auto-generated method stub
                        //list0装载的都是cpdata的数据,list1装载的是info的数据
                       &nbsp;List<Text> list0 = new ArrayList<Text>();     
			List<Text> list1 = new ArrayList<Text>();
			Iterator<TextPair> it = values.iterator();
			TextPair pair = new TextPair();
			while(it.hasNext()){
					pair = it.next();
				if("1".equals(pair.getFlag()))
					list1.add(new Text(pair.getValue()));
				else 
					list0.add(new Text(pair.getValue()));
			}
			
			List<Text> sublist = new ArrayList<Text>();       //sublist用来添加已经写过的数据,然后再判断，如果存在就不用操作
			for(int i = 0 ; i<list1.size(); i++){
				for(int j = 0 ;j<list0.size();j++){
					if(!sublist.contains(list0.get(j))){
						sublist.add(list0.get(j));
						context.write(key, new Text(list0.get(j)+" " +list1.get(i)));
					}
				}
			}
			
			
		}
		
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
&nbsp;                      int res = ToolRunner.run(new Configuration(), new Join(), args);
			System.exit(res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = new Configuration();
		
		FileSystem fs = FileSystem.get(conf);
		if(fs.exists(new Path(args[2]))){
                        //如果文件已近存在就删除文件
//			System.out.println("error : file is exists");
//			System.exit(-1);
			fs.delete(new Path(args[2]), true);
		}
		
		Job job = new Job(conf , "Advanced");
		job.setJarByClass(Join.class);
		job.setMapperClass(AdMap.class);
		job.setReducerClass(AdReduce.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(TextPair.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]),new Path(args[1]));
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		return job.waitForCompletion(true) ? 0 : 1;
	}
	
	
	
	public static class TextPair implements WritableComparable<TextPair>{

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return " " + key +" "+ value; 
		}

		public String getFlag() {
			return flag;
		}

		public void setFlag(String flag) {
			this.flag = flag;
		}

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		private String flag = "";
		private String key ="";
		private String value ="";
		private String content = "";
		
		

		public TextPair(String flag, String key, String value, String content) {
			this.flag = flag;
			this.key = key;
			this.value = value;
			this.content = content;
		}

		public TextPair() {
		}

		@Override
		public void write(DataOutput out) throws IOException {
			// TODO Auto-generated method stub
			out.writeUTF(this.flag);
			out.writeUTF(this.key);
			out.writeUTF(this.value);
			out.writeUTF(this.content);
		}

		@Override
		public void readFields(DataInput in) throws IOException {
			// TODO Auto-generated method stub
			this.flag = in.readUTF();
			this.key = in.readUTF();
			this.value = in.readUTF();
			this.content = in.readUTF();
		}

		@Override
		public int compareTo(TextPair o) {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
	}
	
	
	

}
