#!/bin/bash  
  
#1.数据库信息定义  
mysql_host="localhost"  
mysql_user="root"  
mysql_passwd="root"  
  
#sql备份目录  
root_dir="/backup"  
back_dir="/backup/databases"  
data_dir="databases"  
store_dir="database"  
if [ ! -d $back_dir ]; then  
    mkdir -p $back_dir  
fi  
  
#备份的数据库数组  
db_arr=$(echo "show databases;" | mysql -u$mysql_user -p$mysql_passwd -h$mysql_host)  
#不需要备份的单例数据库  
nodeldb="test1"  
  
#当前日期  
date=$(date -d '+0 days' +%Y%m%d)  
  
#zip打包密码  
zippasswd="passwd"  
zipname="lczh_"$date".zip"  
  
  
#2.进入到备份目录  
cd $back_dir  
  
  
#3.循环备份  
for dbname in ${db_arr}  
do  
    if [ $dbname != $nodeldb ]; then  
        sqlfile=$dbname-$date".sql"  
        mysqldump -u$mysql_user -p$mysql_passwd -h$mysql_host $dbname >$sqlfile  
    fi  
done  
  
  
#4.tar打包所有的sql文件  
tar -zcPpf $root_dir/$store_dir/$zipname --directory /  $root_dir/$data_dir  
#打包成功后删除sql文件  
if [ $? = 0 ]; then  
    rm -r $data_dir  
fi