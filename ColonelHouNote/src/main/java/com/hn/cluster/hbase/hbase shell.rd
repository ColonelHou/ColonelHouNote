1). create 命令
创建一个具有两个列族“grad”和“course”的表“scores”。其中表名、行和列都要用单引号括起来,并以逗号隔开。
hbase(main):012:0> create 'scores', 'name', 'grad', 'course'

2). list 命令
查看当前 HBase 中具有哪些表。
hbase(main):012:0> list

3). describe 命令
查看表“scores”的构造。
hbase(main):012:0> describe 'scores'

4). put 命令
使用 put 命令向表中插入数据,参数分别为表名、行名、列名和值,其中列名前需要列族最为前缀,时间戳由系统自动生成。
格式: put 表名,行名,列名([列族:列名]),值
例子：
a. 加入一行数据,行名称为“xiapi”,列族“grad”的列名为”(空字符串)”,值位 1。
hbase(main):012:0> put 'scores', 'xiapi', 'grad:', '1'
hbase(main):012:0> put 'scores', 'xiapi', 'grad:', '2' --修改操作(update)
b. 给“xiapi”这一行的数据的列族“course”添加一列“<china,97>”。
hbase(main):012:0> put 'scores', 'xiapi',  'course:china', '97'
hbase(main):012:0> put 'scores', 'xiapi',  'course:math', '128'
hbase(main):012:0> put 'scores', 'xiapi',  'course:english', '85'

5). get 命令
a.查看表“scores”中的行“xiapi”的相关数据。
hbase(main):012:0> get 'scores', 'xiapi'
b.查看表“scores”中行“xiapi”列“course :math”的值。
hbase(main):012:0> get 'scores', 'xiapi', 'course :math'
或者
hbase(main):012:0> get 'scores', 'xiapi', {COLUMN=>'course:math'}
hbase(main):012:0> get 'scores', 'xiapi', {COLUMNS=>'course:math'}
备注:COLUMN 和 COLUMNS 是不同的,scan 操作中的 COLUMNS 指定的是表的列族, get操作中的 COLUMN 指定的是特定的列,COLUMNS 的值实质上为“列族:列修饰符”。COLUMN 和 COLUMNS 必须为大写。

6). scan 命令
a. 查看表“scores”中的所有数据。
hbase(main):012:0> scan 'scores'
注意:
scan 命令可以指定 startrow,stoprow 来 scan 多个 row。
例如:
scan 'user_test',{COLUMNS =>'info:username',LIMIT =>10, STARTROW => 'test', STOPROW=>'test2'}
b.查看表“scores”中列族“course”的所有数据。
hbase(main):012:0> scan  'scores', {COLUMN => 'grad'}
hbase(main):012:0> scan  'scores', {COLUMN=>'course:math'}
hbase(main):012:0> scan  'scores', {COLUMNS => 'course'}
hbase(main):012:0> scan  'scores', {COLUMNS => 'course'}

7). count 命令
hbase(main):068:0> count 'scores'

8). exists 命令
hbase(main):071:0> exists 'scores'

9). incr 命令(赋值)

10). delete 命令
删除表“scores”中行为“xiaoxue”, 列族“course”中的“math”。
hbase(main):012:0>  delete 'scores', 'xiapi', 'course:math'

11). truncate 命令
hbase(main):012:0>  truncate 'scores'

12). disbale、drop 命令
通过“disable”和“drop”命令删除“scores”表。
hbase(main):012:0>  disable 'scores' --enable 'scores' 
hbase(main):012:0>  drop 'scores'

13).  status命令
hbase(main):072:0> status

14).  version命令
hbase(main):073:0> version
