#!/usr/bin/env bash

ssh hadoop@hadoopMasterTest ". /usr/local/src/hadoop/etc/hadoop/rm.sh"
ssh hadoop@hadoopSlave1Test ". /usr/local/src/hadoop/etc/hadoop/rm.sh"
ssh hadoop@hadoopSlave2Test ". /usr/local/src/hadoop/etc/hadoop/rm.sh"
ssh hadoop@hadoopSlave3Test ". /usr/local/src/hadoop/etc/hadoop/rm.sh"
ssh hadoop@hadoopSlave4Test ". /usr/local/src/hadoop/etc/hadoop/rm.sh"

#scp -rv /usr/local/src/hadoop/data_conf/* hadoop@hadoopSlave1Test:/usr/local/src/hadoop/data_conf/
#scp -rv /usr/local/src/hadoop/data_conf/* hadoop@hadoopSlave2Test:/usr/local/src/hadoop/data_conf/
#scp -rv /usr/local/src/hadoop/data_conf/* hadoop@hadoopSlave3Test:/usr/local/src/hadoop/data_conf/
#scp -rv /usr/local/src/hadoop/data_conf/* hadoop@hadoopSlave4Test:/usr/local/src/hadoop/data_conf/
