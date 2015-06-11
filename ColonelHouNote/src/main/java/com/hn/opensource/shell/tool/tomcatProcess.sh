http://wuzhaohuixy-qq-com.iteye.com/blog/2180238
#Author : wuchaohui  
#Desc : restart tomcat  
#Time : 2015-1-1  

#!bin/bash

tomcatpath="/softs/tomcat7"

pidlist=`ps -ef|grep $tomcatpath |grep -v "grep"|awk '{print $2}'`  

case $1 in
     "start")

	if [ "$pidlist" = "" ];  then
        	cd $tomcatpath/bin
        	./startup.sh
		sleep 1
		pidlist=`ps -ef|grep $tomcatpath |grep -v "grep"|awk '{print $2}'`
		echo "tomcat start successfully.tomcat pid:$pidlist"
        	cd -
        else
                echo "tomcat is running. pid :  $pidlist"
        fi
	;;
	
     "stop")

	if [ "$pidlist" = "" ];  then
                echo "no tomcat pid alive!"
        else
                echo "tomcat pid list :  $pidlist"
                echo "killing pidlist :  $pidlist"
                kill -9 $pidlist
                echo "tomcat stopped successfully!"
                sleep 1
        fi
	;;

     "restart")
	
	if [ "$pidlist" = "" ];  then  
       		echo "no tomcat pid alive!"  
	else  
 		echo "tomcat pid list :  $pidlist"  
  		echo "killing pidlist :  $pidlist"  
  		kill -9 $pidlist  
  		echo "tomcat stopped successfully!"   
  		sleep 1   
	fi  
	echo "now starting tomcat......"  
  
	cd $tomcatpath/bin  
	./startup.sh  
	cd -   
	;;

     "-help")
	echo ""
	echo "-start, start service."
	echo "        if servie is running ,then Command will not excute."
	echo ""
	echo "-stop, stop service."
	echo "       if service is running ,then stop service."
	echo "       but service is not started,then nothing to do."
	echo ""
	echo "-restart, restart service."
	echo "        if service is running,then stop service and start service."
	echo "        but service is stop then start service too."
	echo ""	
	;;

	"")	
	echo "you must input param."
	;;

	*)
	echo "param error."
	echo $0 " -help to get more info."
	;;
esac



