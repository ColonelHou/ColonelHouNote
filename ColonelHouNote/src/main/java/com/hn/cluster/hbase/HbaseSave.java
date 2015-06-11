package com.hn.cluster.hbase;

import java.io.IOException;
import java.util.Date;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseSave {

    public static void main(String[] args) throws Exception {
        //建表 create 'gpsinfo','gpsdata'
        Configuration HBASE_CONFIG = new Configuration();    
        HBASE_CONFIG.set("hbase.zookeeper.quorum", "192.168.3.206");  
        Configuration configuration = HBaseConfiguration.create(HBASE_CONFIG);  
        try {  
            HBaseAdmin admin =new HBaseAdmin(configuration);
            if (admin.tableExists("gpsinfo")) {
                System.out.println("表已经存在！");
                HTable table = new HTable(configuration, "gpsinfo");  
                Put put = new Put(Bytes.toBytes("0800025364"+new Date().getTime()));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("unitid"),Bytes.toBytes("0800025364"));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("lon"),Bytes.toBytes(111.321f));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("lat"),Bytes.toBytes(23.4687f));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("direction"),Bytes.toBytes(180));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("speed"),Bytes.toBytes(62.2f));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("locationstatus"),Bytes.toBytes(1));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("vehiclestatus"),Bytes.toBytes("点火"));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("gpstime"),Bytes.toBytes(new Date().getTime()));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("referenceposition"),Bytes.toBytes("意隆路22号"));  
                put.add(Bytes.toBytes("gpsdata"),Bytes.toBytes("rawdata"),Bytes.toBytes("7b334673124545711133447852217d"));  
                table.put(put);  
                table.close();
            }else{
                System.out.println("表不存在");
            }
            configuration.clear();
        } catch (IOException e) {  
            e.printStackTrace();  
        }
    }
 
}


