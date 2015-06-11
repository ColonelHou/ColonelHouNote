前面已经讲过如何将log4j的日志输出到指定的hdfs目录，我们前面的指定目录为/flume/events。

如果想用hive来分析采集来的日志，我们可以将/flume/events下面的日志数据都load到hive中的表当中去。


如果了解hive的load data原理的话，还有一种更简便的方式，可以省去load data这一步，就是直接将sink1.hdfs.path指定为hive表的目录。

下面我将详细描述具体的操作步骤。

我们还是从需求驱动来讲解，前面我们采集的数据，都是接口的访问日志数据，数据格式是JSON格式如下：

{"requestTime":1405651379758,"requestParams":{"timestamp":1405651377211,"phone":"02038824941","cardName":"测试商家名称","provinceCode":"440000","cityCode":"440106"},"requestUrl":"/reporter-api/reporter/reporter12/init.do"}

现在有一个需求，我们要统计接口的总调用量。

我第一想法就是，hive中建一张表：test             然后将hdfs.path指定为tier1.sinks.sink1.hdfs.path=hdfs://master68:8020/user/hive/warehouse/besttone.db/test

然后select  count(*) from test;   完事。

这个方案简单，粗暴，先这么干着。于是会遇到一个问题，我的日志数据时JSON格式的，需要hive来序列化和反序列化JSON格式的数据到test表的具体字段当中去。

这有点糟糕，因为hive本身没有提供JSON的SERDE,但是有提供函数来解析JSON字符串，

第一个是（UDF）:

 get_json_object(string json_string,string path) 从给定路径上的JSON字符串中抽取出JSON对象，并返回这个对象的JSON字符串形式，如果输入的JSON字符串是非法的，则返回NULL。

第二个是表生成函数（UDTF）：json_tuple(string jsonstr,p1,p2,...,pn) 本函数可以接受多个标签名称，对输入的JSON字符串进行处理，这个和get_json_object这个UDF类似，不过更高效，其通过一次调用就可以获得多个键值，例：select b.* from test_json a lateral view json_tuple(a.id,'id','name') b as f1,f2;通过lateral view行转列。


最理想的方式就是能有一种JSON SERDE，只要我们LOAD完数据，就直接可以select * from test,而不是select get_json_object这种方式来获取，N个字段就要解析N次，效率太低了。

好在cloudrea wiki里提供了一个json serde类（这个类没有在发行的hive的jar包中），于是我把它搬来了，如下：

[java] view plaincopy
package com.besttone.hive.serde;  
  
import java.util.ArrayList;  
import java.util.Arrays;  
import java.util.HashMap;  
import java.util.List;  
import java.util.Map;  
import java.util.Properties;  
  
import org.apache.hadoop.conf.Configuration;  
import org.apache.hadoop.hive.serde.serdeConstants;  
import org.apache.hadoop.hive.serde2.SerDe;  
import org.apache.hadoop.hive.serde2.SerDeException;  
import org.apache.hadoop.hive.serde2.SerDeStats;  
import org.apache.hadoop.hive.serde2.objectinspector.ListObjectInspector;  
import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;  
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;  
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;  
import org.apache.hadoop.hive.serde2.objectinspector.StructField;  
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;  
import org.apache.hadoop.hive.serde2.typeinfo.ListTypeInfo;  
import org.apache.hadoop.hive.serde2.typeinfo.MapTypeInfo;  
import org.apache.hadoop.hive.serde2.typeinfo.StructTypeInfo;  
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfo;  
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoFactory;  
import org.apache.hadoop.hive.serde2.typeinfo.TypeInfoUtils;  
import org.apache.hadoop.io.Text;  
import org.apache.hadoop.io.Writable;  
import org.codehaus.jackson.map.ObjectMapper;  
  
/** 
 * This SerDe can be used for processing JSON data in Hive. It supports 
 * arbitrary JSON data, and can handle all Hive types except for UNION. However, 
 * the JSON data is expected to be a series of discrete records, rather than a 
 * JSON array of objects. 
 *  
 * The Hive table is expected to contain columns with names corresponding to 
 * fields in the JSON data, but it is not necessary for every JSON field to have 
 * a corresponding Hive column. Those JSON fields will be ignored during 
 * queries. 
 *  
 * Example: 
 *  
 * { "a": 1, "b": [ "str1", "str2" ], "c": { "field1": "val1" } } 
 *  
 * Could correspond to a table: 
 *  
 * CREATE TABLE foo (a INT, b ARRAY<STRING>, c STRUCT<field1:STRING>); 
 *  
 * JSON objects can also interpreted as a Hive MAP type, so long as the keys and 
 * values in the JSON object are all of the appropriate types. For example, in 
 * the JSON above, another valid table declaraction would be: 
 *  
 * CREATE TABLE foo (a INT, b ARRAY<STRING>, c MAP<STRING,STRING>); 
 *  
 * Only STRING keys are supported for Hive MAPs. 
 */  
public class JSONSerDe implements SerDe {  
  
    private StructTypeInfo rowTypeInfo;  
    private ObjectInspector rowOI;  
    private List<String> colNames;  
    private List<Object> row = new ArrayList<Object>();  
  
    //遇到非JSON格式输入的时候的处理。  
    private boolean ignoreInvalidInput;  
  
    /** 
     * An initialization function used to gather information about the table. 
     * Typically, a SerDe implementation will be interested in the list of 
     * column names and their types. That information will be used to help 
     * perform actual serialization and deserialization of data. 
     */  
    @Override  
    public void initialize(Configuration conf, Properties tbl)  
            throws SerDeException {  
        // 遇到无法转换成JSON对象的字符串时，是否忽略，默认不忽略，抛出异常，设置为true将跳过异常。  
        ignoreInvalidInput = Boolean.valueOf(tbl.getProperty(  
                "input.invalid.ignore", "false"));  
  
        // Get a list of the table's column names.  
  
        String colNamesStr = tbl.getProperty(serdeConstants.LIST_COLUMNS);  
        colNames = Arrays.asList(colNamesStr.split(","));  
  
        // Get a list of TypeInfos for the columns. This list lines up with  
        // the list of column names.  
        String colTypesStr = tbl.getProperty(serdeConstants.LIST_COLUMN_TYPES);  
        List<TypeInfo> colTypes = TypeInfoUtils  
                .getTypeInfosFromTypeString(colTypesStr);  
  
        rowTypeInfo = (StructTypeInfo) TypeInfoFactory.getStructTypeInfo(  
                colNames, colTypes);  
        rowOI = TypeInfoUtils  
                .getStandardJavaObjectInspectorFromTypeInfo(rowTypeInfo);  
    }  
  
    /** 
     * This method does the work of deserializing a record into Java objects 
     * that Hive can work with via the ObjectInspector interface. For this 
     * SerDe, the blob that is passed in is a JSON string, and the Jackson JSON 
     * parser is being used to translate the string into Java objects. 
     *  
     * The JSON deserialization works by taking the column names in the Hive 
     * table, and looking up those fields in the parsed JSON object. If the 
     * value of the field is not a primitive, the object is parsed further. 
     */  
    @Override  
    public Object deserialize(Writable blob) throws SerDeException {  
        Map<?, ?> root = null;  
        row.clear();  
        try {  
            ObjectMapper mapper = new ObjectMapper();  
            // This is really a Map<String, Object>. For more information about  
            // how  
            // Jackson parses JSON in this example, see  
            // http://wiki.fasterxml.com/JacksonDataBinding  
            root = mapper.readValue(blob.toString(), Map.class);  
        } catch (Exception e) {  
            // 如果为true,不抛出异常，忽略该行数据  
            if (!ignoreInvalidInput)  
                throw new SerDeException(e);  
            else {  
                return null;  
            }  
              
        }  
  
        // Lowercase the keys as expected by hive  
        Map<String, Object> lowerRoot = new HashMap();  
        for (Map.Entry entry : root.entrySet()) {  
            lowerRoot.put(((String) entry.getKey()).toLowerCase(),  
                    entry.getValue());  
        }  
        root = lowerRoot;  
  
        Object value = null;  
        for (String fieldName : rowTypeInfo.getAllStructFieldNames()) {  
            try {  
                TypeInfo fieldTypeInfo = rowTypeInfo  
                        .getStructFieldTypeInfo(fieldName);  
                value = parseField(root.get(fieldName), fieldTypeInfo);  
            } catch (Exception e) {  
                value = null;  
            }  
            row.add(value);  
        }  
        return row;  
    }  
  
    /** 
     * Parses a JSON object according to the Hive column's type. 
     *  
     * @param field 
     *            - The JSON object to parse 
     * @param fieldTypeInfo 
     *            - Metadata about the Hive column 
     * @return - The parsed value of the field 
     */  
    private Object parseField(Object field, TypeInfo fieldTypeInfo) {  
        switch (fieldTypeInfo.getCategory()) {  
        case PRIMITIVE:  
            // Jackson will return the right thing in this case, so just return  
            // the object  
            if (field instanceof String) {  
                field = field.toString().replaceAll("\n", "\\\\n");  
            }  
            return field;  
        case LIST:  
            return parseList(field, (ListTypeInfo) fieldTypeInfo);  
        case MAP:  
            return parseMap(field, (MapTypeInfo) fieldTypeInfo);  
        case STRUCT:  
            return parseStruct(field, (StructTypeInfo) fieldTypeInfo);  
        case UNION:  
            // Unsupported by JSON  
        default:  
            return null;  
        }  
    }  
  
    /** 
     * Parses a JSON object and its fields. The Hive metadata is used to 
     * determine how to parse the object fields. 
     *  
     * @param field 
     *            - The JSON object to parse 
     * @param fieldTypeInfo 
     *            - Metadata about the Hive column 
     * @return - A map representing the object and its fields 
     */  
    private Object parseStruct(Object field, StructTypeInfo fieldTypeInfo) {  
        Map<Object, Object> map = (Map<Object, Object>) field;  
        ArrayList<TypeInfo> structTypes = fieldTypeInfo  
                .getAllStructFieldTypeInfos();  
        ArrayList<String> structNames = fieldTypeInfo.getAllStructFieldNames();  
  
        List<Object> structRow = new ArrayList<Object>(structTypes.size());  
        for (int i = 0; i < structNames.size(); i++) {  
            structRow.add(parseField(map.get(structNames.get(i)),  
                    structTypes.get(i)));  
        }  
        return structRow;  
    }  
  
    /** 
     * Parse a JSON list and its elements. This uses the Hive metadata for the 
     * list elements to determine how to parse the elements. 
     *  
     * @param field 
     *            - The JSON list to parse 
     * @param fieldTypeInfo 
     *            - Metadata about the Hive column 
     * @return - A list of the parsed elements 
     */  
    private Object parseList(Object field, ListTypeInfo fieldTypeInfo) {  
        ArrayList<Object> list = (ArrayList<Object>) field;  
        TypeInfo elemTypeInfo = fieldTypeInfo.getListElementTypeInfo();  
  
        for (int i = 0; i < list.size(); i++) {  
            list.set(i, parseField(list.get(i), elemTypeInfo));  
        }  
  
        return list.toArray();  
    }  
  
    /** 
     * Parse a JSON object as a map. This uses the Hive metadata for the map 
     * values to determine how to parse the values. The map is assumed to have a 
     * string for a key. 
     *  
     * @param field 
     *            - The JSON list to parse 
     * @param fieldTypeInfo 
     *            - Metadata about the Hive column 
     * @return 
     */  
    private Object parseMap(Object field, MapTypeInfo fieldTypeInfo) {  
        Map<Object, Object> map = (Map<Object, Object>) field;  
        TypeInfo valueTypeInfo = fieldTypeInfo.getMapValueTypeInfo();  
  
        for (Map.Entry<Object, Object> entry : map.entrySet()) {  
            map.put(entry.getKey(), parseField(entry.getValue(), valueTypeInfo));  
        }  
        return map;  
    }  
  
    /** 
     * Return an ObjectInspector for the row of data 
     */  
    @Override  
    public ObjectInspector getObjectInspector() throws SerDeException {  
        return rowOI;  
    }  
  
    /** 
     * Unimplemented 
     */  
    @Override  
    public SerDeStats getSerDeStats() {  
        return null;  
    }  
  
    /** 
     * JSON is just a textual representation, so our serialized class is just 
     * Text. 
     */  
    @Override  
    public Class<? extends Writable> getSerializedClass() {  
        return Text.class;  
    }  
  
    /** 
     * This method takes an object representing a row of data from Hive, and 
     * uses the ObjectInspector to get the data for each column and serialize 
     * it. This implementation deparses the row into an object that Jackson can 
     * easily serialize into a JSON blob. 
     */  
    @Override  
    public Writable serialize(Object obj, ObjectInspector oi)  
            throws SerDeException {  
        Object deparsedObj = deparseRow(obj, oi);  
        ObjectMapper mapper = new ObjectMapper();  
        try {  
            // Let Jackson do the work of serializing the object  
            return new Text(mapper.writeValueAsString(deparsedObj));  
        } catch (Exception e) {  
            throw new SerDeException(e);  
        }  
    }  
  
    /** 
     * Deparse a Hive object into a Jackson-serializable object. This uses the 
     * ObjectInspector to extract the column data. 
     *  
     * @param obj 
     *            - Hive object to deparse 
     * @param oi 
     *            - ObjectInspector for the object 
     * @return - A deparsed object 
     */  
    private Object deparseObject(Object obj, ObjectInspector oi) {  
        switch (oi.getCategory()) {  
        case LIST:  
            return deparseList(obj, (ListObjectInspector) oi);  
        case MAP:  
            return deparseMap(obj, (MapObjectInspector) oi);  
        case PRIMITIVE:  
            return deparsePrimitive(obj, (PrimitiveObjectInspector) oi);  
        case STRUCT:  
            return deparseStruct(obj, (StructObjectInspector) oi, false);  
        case UNION:  
            // Unsupported by JSON  
        default:  
            return null;  
        }  
    }  
  
    /** 
     * Deparses a row of data. We have to treat this one differently from other 
     * structs, because the field names for the root object do not match the 
     * column names for the Hive table. 
     *  
     * @param obj 
     *            - Object representing the top-level row 
     * @param structOI 
     *            - ObjectInspector for the row 
     * @return - A deparsed row of data 
     */  
    private Object deparseRow(Object obj, ObjectInspector structOI) {  
        return deparseStruct(obj, (StructObjectInspector) structOI, true);  
    }  
  
    /** 
     * Deparses struct data into a serializable JSON object. 
     *  
     * @param obj 
     *            - Hive struct data 
     * @param structOI 
     *            - ObjectInspector for the struct 
     * @param isRow 
     *            - Whether or not this struct represents a top-level row 
     * @return - A deparsed struct 
     */  
    private Object deparseStruct(Object obj, StructObjectInspector structOI,  
            boolean isRow) {  
        Map<Object, Object> struct = new HashMap<Object, Object>();  
        List<? extends StructField> fields = structOI.getAllStructFieldRefs();  
        for (int i = 0; i < fields.size(); i++) {  
            StructField field = fields.get(i);  
            // The top-level row object is treated slightly differently from  
            // other  
            // structs, because the field names for the row do not correctly  
            // reflect  
            // the Hive column names. For lower-level structs, we can get the  
            // field  
            // name from the associated StructField object.  
            String fieldName = isRow ? colNames.get(i) : field.getFieldName();  
            ObjectInspector fieldOI = field.getFieldObjectInspector();  
            Object fieldObj = structOI.getStructFieldData(obj, field);  
            struct.put(fieldName, deparseObject(fieldObj, fieldOI));  
        }  
        return struct;  
    }  
  
    /** 
     * Deparses a primitive type. 
     *  
     * @param obj 
     *            - Hive object to deparse 
     * @param oi 
     *            - ObjectInspector for the object 
     * @return - A deparsed object 
     */  
    private Object deparsePrimitive(Object obj, PrimitiveObjectInspector primOI) {  
        return primOI.getPrimitiveJavaObject(obj);  
    }  
  
    private Object deparseMap(Object obj, MapObjectInspector mapOI) {  
        Map<Object, Object> map = new HashMap<Object, Object>();  
        ObjectInspector mapValOI = mapOI.getMapValueObjectInspector();  
        Map<?, ?> fields = mapOI.getMap(obj);  
        for (Map.Entry<?, ?> field : fields.entrySet()) {  
            Object fieldName = field.getKey();  
            Object fieldObj = field.getValue();  
            map.put(fieldName, deparseObject(fieldObj, mapValOI));  
        }  
        return map;  
    }  
  
    /** 
     * Deparses a list and its elements. 
     *  
     * @param obj 
     *            - Hive object to deparse 
     * @param oi 
     *            - ObjectInspector for the object 
     * @return - A deparsed object 
     */  
    private Object deparseList(Object obj, ListObjectInspector listOI) {  
        List<Object> list = new ArrayList<Object>();  
        List<?> field = listOI.getList(obj);  
        ObjectInspector elemOI = listOI.getListElementObjectInspector();  
        for (Object elem : field) {  
            list.add(deparseObject(elem, elemOI));  
        }  
        return list;  
    }  
}  

我稍微修改了一点东西，多加了一个参数input.invalid.ignore,对应的变量为：
//遇到非JSON格式输入的时候的处理。
private boolean ignoreInvalidInput;

在deserialize方法中原来是如果传入的是非JSON格式字符串的话，直接抛出了SerDeException，我加了一个参数来控制它是否抛出异常，在initialize方法中初始化这个变量（默认为false）：


// 遇到无法转换成JSON对象的字符串时，是否忽略，默认不忽略，抛出异常，设置为true将跳过异常。
ignoreInvalidInput = Boolean.valueOf(tbl.getProperty(
"input.invalid.ignore", "false"));

好的，现在将这个类打成JAR包： JSONSerDe.jar，放在hive_home的auxlib目录下（我的是/etc/hive/auxlib），然后修改hive-env.sh，添加HIVE_AUX_JARS_PATH=/etc/hive/auxlib/JSONSerDe.jar，这样每次运行hive客户端的时候都会将这个jar包添加到classpath，否则在设置SERDE的时候会报找不到类。

现在我们在HIVE中创建一张表用来存放日志数据：

[plain] view plaincopy
create table test(  
requestTime BIGINT,  
requestParams STRUCT<timestamp:BIGINT,phone:STRING,cardName:STRING,provinceCode:STRING,cityCode:STRING>,    
requestUrl STRING)  
 row format serde "com.besttone.hive.serde.JSONSerDe"   
 WITH SERDEPROPERTIES(  
 "input.invalid.ignore"="true",  
 "requestTime"="$.requestTime",  
 "requestParams.timestamp"="$.requestParams.timestamp",  
 "requestParams.phone"="$.requestParams.phone",  
 "requestParams.cardName"="$.requestParams.cardName",  
 "requestParams.provinceCode"="$.requestParams.provinceCode",  
 "requestParams.cityCode"="$.requestParams.cityCode",  
 "requestUrl"="$.requestUrl");  

这个表结构就是按照日志格式设计的，还记得前面说过的日志数据如下：
{"requestTime":1405651379758,"requestParams":{"timestamp":1405651377211,"phone":"02038824941","cardName":"测试商家名称","provinceCode":"440000","cityCode":"440106"},"requestUrl":"/reporter-api/reporter/reporter12/init.do"}

我使用了一个STRUCT类型来保存requestParams的值，row format我们用的是自定义的json serde:com.besttone.hive.serde.JSONSerDe，SERDEPROPERTIES中，除了设置JSON对象的映射关系外，我还设置了一个自定义的参数："input.invalid.ignore"="true"，忽略掉所有非JSON格式的输入行。这里不是真正意义的忽略，只是非法行的每个输出字段都为NULL了，要在结果集上忽略，必须这样写：select * from test where requestUrl is not null;

OK表建好了，现在就差数据了，我们启动flumedemo的WriteLog,往hive表test目录下面输出一些日志数据，然后在进入hive客户端，select * from test;所以字段都正确的解析，大功告成。

flume.conf如下：

[plain] view plaincopy
tier1.sources=source1  
tier1.channels=channel1  
tier1.sinks=sink1  
  
tier1.sources.source1.type=avro  
tier1.sources.source1.bind=0.0.0.0  
tier1.sources.source1.port=44444  
tier1.sources.source1.channels=channel1  
  
tier1.sources.source1.interceptors=i1 i2  
tier1.sources.source1.interceptors.i1.type=regex_filter  
tier1.sources.source1.interceptors.i1.regex=\\{.*\\}  
tier1.sources.source1.interceptors.i2.type=timestamp  
  
tier1.channels.channel1.type=memory  
tier1.channels.channel1.capacity=10000  
tier1.channels.channel1.transactionCapacity=1000  
tier1.channels.channel1.keep-alive=30  
  
tier1.sinks.sink1.type=hdfs  
tier1.sinks.sink1.channel=channel1  
tier1.sinks.sink1.hdfs.path=hdfs://master68:8020/user/hive/warehouse/besttone.db/test  
tier1.sinks.sink1.hdfs.fileType=DataStream  
tier1.sinks.sink1.hdfs.writeFormat=Text  
tier1.sinks.sink1.hdfs.rollInterval=0  
tier1.sinks.sink1.hdfs.rollSize=10240  
tier1.sinks.sink1.hdfs.rollCount=0  
tier1.sinks.sink1.hdfs.idleTimeout=60  

besttone.db是我在hive中创建的数据库，了解hive的应该理解没多大问题。

OK，到这篇文章为止，整个从LOG4J生产日志，到flume收集日志，再到用hive离线分析日志，一整套流水线都讲解完了。

上一篇flume学习（五）：Flume Channel Selectors使用
下一篇flume学习（七）：如何使用event header中的key值
http://blog.csdn.net/xiao_jun_0820/article/details/38119123