[hadoop@hadoopMaster ]$sudo yum install crontabs
service crond start //启动服务
service crond stop //关闭服务
service crond restart //重启服务
service crond reload //重新载入配置

crontab命令：
	参　　数：
	-e 　编辑该用户的计时器设置。
	-l 　列出该用户的计时器设置。
	-r 　删除该用户的计时器设置。
	-u<用户名称> 　指定要设定计时器的用户名称。
基本格式 :
	* *　 *　 *　 *　　command
	分　时　日　月　周　 命令
	
	第1列表示分钟1～59 每分钟用*或者 */1表示
	第2列表示小时1～23（0表示0点）
	第3列表示日期1～31
	第4列 表示月份1～12
	第5列标识号星期0～6（0表示星期天）
	第6列要运行的命令
例子：
	30 21 * * * /etc/init.d/nginx restart
	每晚的21:30重启 nginx。
	
	45 4 1,10,22 * * /etc/init.d/nginx restart
	每月1、 10、22日的4 : 45重启nginx。
	
	10 1 * * 6,0 /etc/init.d/nginx restart
	每周六、周日的1 : 10重启nginx。
	
	0,30 18-23 * * * /etc/init.d/nginx restart
	每天18 : 00至23 : 00之间每隔30分钟重启nginx。
	
	0 23 * * 6 /etc/init.d/nginx restart
	每星期六的11 : 00 pm重启nginx。
	
	* */1 * * * /etc/init.d/nginx restart
	每一小时重启nginx
	
	* 23-7/1 * * * /etc/init.d/nginx restart
	晚上11点到早上7点之间，每 隔一小时重启nginx
	
	0 11 4 * mon-wed /etc/init.d/nginx restart
	每月的4号与每周一到周三 的11点重启nginx
	
	0 4 1 jan * /etc/init.d/nginx restart
	一月一号的4点重启nginx
	
	*/30 * * * * /usr/sbin/ntpdate 210.72.145.20
	每半小时同步一下时间