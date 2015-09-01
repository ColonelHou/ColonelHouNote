一、if的基本语法:
if [ command ];then
   符合该条件执行的语句
elif [ command ];then
   符合该条件执行的语句
else
   符合该条件执行的语句
fi

二、文件/文件夹(目录)判断
[ -b FILE ] 如果 FILE 存在且是一个块特殊文件则为真。
[ -c FILE ] 如果 FILE 存在且是一个字特殊文件则为真。
[ -d DIR ] 如果 FILE 存在且是一个目录则为真。
[ -e FILE ] 如果 FILE 存在则为真。
[ -f FILE ] 如果 FILE 存在且是一个普通文件则为真。
[ -g FILE ] 如果 FILE 存在且已经设置了SGID则为真。
[ -k FILE ] 如果 FILE 存在且已经设置了粘制位则为真。
[ -p FILE ] 如果 FILE 存在且是一个名字管道(F如果O)则为真。
[ -r FILE ] 如果 FILE 存在且是可读的则为真。
[ -s FILE ] 如果 FILE 存在且大小不为0则为真。
[ -t FD ] 如果文件描述符 FD 打开且指向一个终端则为真。
[ -u FILE ] 如果 FILE 存在且设置了SUID (set user ID)则为真。
[ -w FILE ] 如果 FILE存在且是可写的则为真。
[ -x FILE ] 如果 FILE 存在且是可执行的则为真。
[ -O FILE ] 如果 FILE 存在且属有效用户ID则为真。
[ -G FILE ] 如果 FILE 存在且属有效用户组则为真。
[ -L FILE ] 如果 FILE 存在且是一个符号连接则为真。
[ -N FILE ] 如果 FILE 存在 and has been mod如果ied since it was last read则为真。
[ -S FILE ] 如果 FILE 存在且是一个套接字则为真。
[ FILE1 -nt FILE2 ] 如果 FILE1 has been changed more recently than FILE2, or 如果 FILE1 exists and FILE2 does not则为真。
[ FILE1 -ot FILE2 ] 如果 FILE1 比 FILE2 要老, 或者 FILE2 存在且 FILE1 不存在则为真。
[ FILE1 -ef FILE2 ] 如果 FILE1 和 FILE2 指向相同的设备和节点号则为真。

三、字符串判断
[ -z STRING ] 如果STRING的长度为零则为真 ，即判断是否为空，空即是真；
[ -n STRING ] 如果STRING的长度非零则为真 ，即判断是否为非空，非空即是真；
[ STRING1 = STRING2 ] 如果两个字符串相同则为真 ；
[ STRING1 != STRING2 ] 如果字符串不相同则为真 ；
[ STRING1 ]　 如果字符串不为空则为真,与-n类似

四、数值判断
INT1 -eq INT2           INT1和INT2两数相等为真 ,=
INT1 -ne INT2           INT1和INT2两数不等为真 ,<>
INT1 -gt INT2            INT1大于INT1为真 ,>
INT1 -ge INT2           INT1大于等于INT2为真,>=
INT1 -lt INT2             INT1小于INT2为真 ,<</div>
INT1 -le INT2             INT1小于等于INT2为真,<=

五、复杂逻辑判断
-a 与
-o 或
! 非

exp1: 如果a>b且a
if (( a > b )) && (( a < c ))
或者
if [[ $a > $b ]] && [[ $a < $c ]]
或者
if [ $a -gt $b -a $a -lt $c ]

exp2:如果a>b或a
if (( a > b )) || (( a < c ))
或者
if [[ $a > $b ]] || [[ $a < $c ]]
或者
if [ $a -gt $b -o $a -lt $c ]
 
"||"和"&&"在SHELL里可以用，也就是第一个写成if [ a>b && a
shell编程之if判断的总结

六、调试及查看shell脚本的执行过程
在执行的时候，通过下面的方式：
#bash -x strtst.sh
shell编程之if判断的总结
或者可以在脚本内部的开头，即#!/bin/sh下增加一行set -x
shell编程之if判断的总结

七、举例
1、if语句的基本结构实现：
exp1:查看当前操作系统类型
#!/bin/sh
SYSTEM=`uname -s`       #获取操作系统类型，我本地是linux
if [ $SYSTEM = "Linux" ] ; then #如果是linux的话打印linux字符串
echo "Linux"
elif [ $SYSTEM = "FreeBSD" ] ; then
echo "FreeBSD"
elif [ $SYSTEM = "Solaris" ] ; then
echo "Solaris"
else
echo "What?"
fi

2、if利用read传参判断
exp2:查看分数
#!/bin/bash
#echo -n "please input your score:"
#read score
#echo "input score is $ score "
read -p "please input a score:" score
echo -e "your score [$score] is judging by sys now"
if [ "$score" -ge "0" ]&&[ "$score" -lt "60" ];then
        echo "sorry,you are lost!"
elif [ "$score" -ge "60" ]&&[ "$score" -lt "85" ];then
        echo "just soso!"
elif [ "$score" -le "100" ]&&[ "$score" -ge "85" ];then
        echo "good job!"
else
        echo "input score is wrong , the range is [0-100]!"
fi

说明：通过echo 输出（#注销掉的）和read -p结果是一样的，只需要注意echo的参数设置。
      echo -e参数使输出中的反斜线（\）的说明起作用
      echo -n参数使引号后的内容接着输出(不换行)
shell编程之if判断的总结

3.注意if判断中对于变量的处理，需要加引号，以免一些不必要的错误！
没有加双引号会在一些含空格等的字符串变量判断的时候产生错误。
shell编程之if判断的总结
加上引号就可以避免这些不必要的错误。
shell编程之if判断的总结

http://blog.sina.com.cn/s/blog_4c197d420101bthf.html