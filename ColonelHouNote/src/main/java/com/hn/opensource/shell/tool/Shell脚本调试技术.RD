ERRTRAP()
{
  echo "[LINE:$1] Error: Command or function exited with status $?"
}
foo()
{
  return 1;
}
trap 'ERRTRAP $LINENO' ERR
abc
foo

trap 'command' signal
其中signal是要捕获的信号，command是捕获到指定的信号之后，所要执行的命令。
#!/bin/bash
trap 'echo “before execute line:$LINENO, a=$a,b=$b,c=$c”' DEBUG
a=1
if [ "$a" -eq 1 ]
then
   b=2
else
   b=1
fi
c=3
echo "end"

参考
http://www.ibm.com/developerworks/cn/linux/l-cn-shell-debug/