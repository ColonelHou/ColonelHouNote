wget http://www.us.apache.org/dist/lucene/solr/4.9.0/solr-4.9.0.tgz  
tar -xcvf solr-4.9.0.tgz  
1、解压并将solr-4.9.0\example\webapps 下的solr.war拷贝到webapps下手动启动tomcat解压solr.war。  
2、建立solr home的位置，这里我是在/usr/local/test/的solr文件夹，并拷贝solr-4.9.0\example下的solr到solr文件夹下。  
sudo vim /etcprofile   
export JAVA_OPTS="$JAVA_OPTS -Dsolr.solr.home=/usr/local/test/solr"  
3、将tomcat\webapps\solr\WEB-INF下的web.xml中以下注解打开并配置solr home的位置指向  
    <env-entry>  
       <env-entry-name>solr/home</env-entry-name>  
       <env-entry-value>/usr/local/test/solr</env-entry-value>  
       <env-entry-type>java.lang.String</env-entry-type>  
    </env-entry>  
4、拷贝solr\example\lib\ext下的所有jar 到tomcat\webapps\solr\WEB-INF\lib下  
5、tomcat\webapps\solr\WEB-INF下新建一个classes文件夹，将solr\example\resources下的log4j.properties拷贝过去  
6、启动tomcat成功后访问http://localhost:8888/solr/就能看到  