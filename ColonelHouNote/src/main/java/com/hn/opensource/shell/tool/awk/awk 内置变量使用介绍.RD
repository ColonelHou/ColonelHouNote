$0	当前记录（作为单个变量）
$1~$n	当前记录的第n个字段，字段间由FS分隔
FS	输入字段分隔符 默认是空格
NF	当前记录中的字段个数，就是有多少列
NR	已经读出的记录数，就是行号，从1开始
RS	输入的记录他隔符默 认为换行符
OFS	输出字段分隔符 默认也是空格
ORS	输出的记录分隔符，默认为换行符
ARGC	命令行参数个数
ARGV	命令行参数数组
FILENAME	当前输入文件的名字
IGNORECASE	如果为真，则进行忽略大小写的匹配
ARGIND	当前被处理文件的ARGV标志符
CONVFMT	数字转换格式 %.6g
ENVIRON	UNIX环境变量
ERRNO	UNIX系统错误消息
FIELDWIDTHS	输入字段宽度的空白分隔字符串
FNR	当前记录数