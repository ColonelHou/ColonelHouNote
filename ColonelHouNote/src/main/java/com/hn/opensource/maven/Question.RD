问题1：
[ERROR] /path/to/source/SoundEngineFilePanel.java:[33,8] error: cannot find symbol
[ERROR]  class SoundEngineFilePanel
把pom.xml中配置maven-compiler-plugin改为3.1   jdk版本改为1.7
<dependency>
	    <groupId>jdk.tools</groupId>
	    <artifactId>jdk.tools</artifactId>
	    <version>1.7</version>
	    <scope>system</scope>
	    <systemPath>${JAVA_HOME}/lib/tools.jar</systemPath>
	</dependency>
	
	
Failure to find maven-plugins:maven-cobertura-plugin:plugin:1.3
搞了好久最后在stackoverflow中找到了答案，原来是 jaxen-1.1.3引用了cobertura，于是
在项目pom中做如下配置
Xml代码  收藏代码
<dependency>  
            <groupId>jaxen</groupId>  
            <artifactId>jaxen</artifactId>  
            <version>1.1.3</version>  
            <exclusions>  
                <exclusion>  
                    <artifactId>maven-cobertura-plugin</artifactId>  
                    <groupId>maven-plugins</groupId>  
                </exclusion>  
                <exclusion>  
                    <artifactId>maven-findbugs-plugin</artifactId>  
                    <groupId>maven-plugins</groupId>  
                </exclusion>  
            </exclusions>  
</dependency>  
 问题得到解决，另外改动版本号估计也可行，还没试
 ==============问题===========
-Dmaven.multiModuleProjectDirectory=$M2_HOME