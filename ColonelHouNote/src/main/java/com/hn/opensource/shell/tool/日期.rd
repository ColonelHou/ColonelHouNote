shell里直接调用系统变量 
获取当天日期`date   +%Y%m%d` 
获取昨天日期`date   -d   yesterday   +%Y%m%d` 
获取前天日期`date   -d   -2day   +%Y%m%d` 
依次类推，你可以写一段shell 试验一下：
d1=`date   +%Y%m%d` 
d2=`date   -d   yesterday   +%Y%m%d` 
d3=`date   -d   -2day   +%Y%m%d` 