 1. shell判断文件,目录是否存在或者具有权限 
2. #!/bin/sh 
3. 
4. myPath="/var/log/httpd/" 
5. myFile="/var /log/httpd/access.log" 
6. 
7. # 这里的-x 参数判断$myPath是否存在并且是否具有可执行权限 
8. if [ ! -x "$myPath"]; then 
9. mkdir "$myPath" 
10. fi 
11. 
12. # 这里的-d 参数判断$myPath是否存在 
13. if [ ! -d "$myPath"]; then 
14. mkdir "$myPath" 
15. fi 
16. 
17. # 这里的-f参数判断$myFile是否存在 
18. if [ ! -f "$myFile" ]; then 
19. touch "$myFile" 
20. fi 
21. 
22. # 其他参数还有-n,-n是判断一个变量是否有值 
23. if [ ! -n "$myVar" ]; then 
24. echo "$myVar is empty" 
25. exit 0 
26. fi 
27. 
28. # 两个变量判断是否相等 
29. if [ "$var1" = "$var2" ]; then 
30. echo '$var1 eq $var2' 
31. else 
32. echo '$var1 not eq $var2' 
33. fi 

-f 和-e的区别 
Conditional Logic on Files 

-a file exists. 
-b file exists and is a block special file. 
-c file exists and is a character special file. 
-d file exists and is a directory. 
-e file exists (just the same as -a). 
-f file exists and is a regular file. 
-g file exists and has its setgid(2) bit set. 
-G file exists and has the same group ID as this process. 
-k file exists and has its sticky bit set. 
-L file exists and is a symbolic link. 
-n string length is not zero. 
-o Named option is set on. 
-O file exists and is owned by the user ID of this process. 
-p file exists and is a first in, first out (FIFO) special file or 
named pipe. 
-r file exists and is readable by the current process. 
-s file exists and has a size greater than zero. 
-S file exists and is a socket. 
-t file descriptor number fildes is open and associated with a 
terminal device. 
-u file exists and has its setuid(2) bit set. 
-w file exists and is writable by the current process. 
-x file exists and is executable by the current process. 
-z string length is zero. 

是用 -s 还是用 -f 这个区别是很大的！