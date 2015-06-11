Hive如何在GROUP BY 后，获取每个GROUP的Top N
SELECT page-id, user-id, clicks
FROM (
    SELECT page-id, user-id, rank(page-id) as rank, clicks FROM (
        SELECT page-id, user-id, clicks FROM mytable
        DISTRIBUTE BY page-id
        SORT BY page-id, clicks desc
) a ) b
WHERE rank < 5
ORDER BY page-id, rank


-----------------Struct---------------------------------
create table struct_test(id int, info struct<name:string, age :int>)row format delimited fields terminated by ','collection items terminated by ':';
struct_test.data
1,jim:20
2,lily:30
3,lucy:25
4,john:34
load data local inpath '/opt/trace/tmp/hive/map_test.data' into table struct_test;
select info.age from struct_test;
---------------------------------------------------------
-----------------Array-----------------------------------
create table array_test(name string, student_id_list array<INT>) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' COLLECTION ITEMS TERMINATED BY ':';
array_test.data
034,1:2:3:4  
035,5:6  
036,7:8:9:10
LOAD DATA LOCAL INPATH '/opt/hive/data/test6.txt' INTO TABLE array_test ;  
select student_id_list[3] from array_test;  
--------------------------------------------------------
-------------------Map----------------------------------
create table map_test(id string, perf map<string, int>) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\t' COLLECTION ITEMS TERMINATED BY ',' MAP KEYS TERMINATED BY ':'; 
map_test.data
1       job:80,team:60,person:70  
2       job:60,team:80  
3       job:90,team:70,person:100
LOAD DATA LOCAL INPATH '/opt/hive/data/map_test.txt' INTO TABLE map_test;
select perf['person'] from map_test;
select perf['person'] from map_test where perf['person'] is not null;
--------------------------------------------------------
