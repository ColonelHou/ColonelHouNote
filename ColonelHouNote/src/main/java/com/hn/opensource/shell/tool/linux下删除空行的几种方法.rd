1、grep

grep -v '^$' file
2、sed

sed '/^$/d'  file 或 sed -n '/./p' file
sed /^[[:space:]]*$/d filename
可以删除内容为多个空格/tab组成的行。

3、awk

awk '/./ {print}' file 或 awk '{if($0!=" ") print}'
4、tr

tr -s "n"
除此之外，vim也可以在查看时。通过命令模式删除空行。vim在命令模式下输入：

%s/^n//g
意思是全局替换所有以回车开头的字符，替换为空。如果有多个连续的空行，想保留一行。则只需在命令行模式输入下行即可：

%s/^n$//g

awk NF data.txt   # 这个也可以将空格、tab等组成的空行删掉。
awk '!/^$/' data.txt