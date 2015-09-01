#!/bin/bash  
  
#1.���ݿ���Ϣ����  
mysql_host="localhost"  
mysql_user="root"  
mysql_passwd="root"  
  
#sql����Ŀ¼  
root_dir="/backup"  
back_dir="/backup/databases"  
data_dir="databases"  
store_dir="database"  
if [ ! -d $back_dir ]; then  
    mkdir -p $back_dir  
fi  
  
#���ݵ����ݿ�����  
db_arr=$(echo "show databases;" | mysql -u$mysql_user -p$mysql_passwd -h$mysql_host)  
#����Ҫ���ݵĵ������ݿ�  
nodeldb="test1"  
  
#��ǰ����  
date=$(date -d '+0 days' +%Y%m%d)  
  
#zip�������  
zippasswd="passwd"  
zipname="lczh_"$date".zip"  
  
  
#2.���뵽����Ŀ¼  
cd $back_dir  
  
  
#3.ѭ������  
for dbname in ${db_arr}  
do  
    if [ $dbname != $nodeldb ]; then  
        sqlfile=$dbname-$date".sql"  
        mysqldump -u$mysql_user -p$mysql_passwd -h$mysql_host $dbname >$sqlfile  
    fi  
done  
  
  
#4.tar������е�sql�ļ�  
tar -zcPpf $root_dir/$store_dir/$zipname --directory /  $root_dir/$data_dir  
#����ɹ���ɾ��sql�ļ�  
if [ $? = 0 ]; then  
    rm -r $data_dir  
fi