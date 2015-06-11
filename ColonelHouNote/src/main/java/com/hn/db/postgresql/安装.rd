添加组
[hadoop@hadoop ~]$sudo groupadd postgres
添加用户到已存在组中
[hadoop@hadoop ~]$sudo useradd postgres -g postgres
新建数据库执行文件目录
[hadoop@hadoop ~]$sudo mkdir -p /usr/local/pgsql
新建数据库数据文件目录
[hadoop@hadoop ~]$sudo mkdir -p /db/pgsql/data
修改目录拥有者
[hadoop@hadoop ~]$sudo chown -R postgres:postgres /usr/local/pgsql/.
[hadoop@hadoop ~]$sudo chown -R postgres:postgres /db/pgsql/data
[hadoop@hadoop ~]$sudo chown -R postgres:postgres /db/pgsql/data/.
配置环境变量
[hadoop@hadoop ~]$sudo vim /etc/profile
PATH=/usr/local/pgsql/bin:$PATH
export PATH
[hadoop@hadoop ~]$source /etc/profile
安装编译源码所需的工具和库
[hadoop@hadoop ~]$sudo yum -y install wget gcc readline-devel zlib-devel make
下载
wget http://ftp.postgresql.org/pub/source/v9.2.4/postgresql-9.2.4.tar.bz2
减压
[hadoop@hadoop ~]$tar -jxvf ./postgresql-9.2.4.tar.bz2
[hadoop@hadoop ~]$cd ./postgresql-9.2.4
源码编译配置脚本
[hadoop@hadoop ~]$sudo ./configure
编译源码
[hadoop@hadoop ~]$sudo make
安装
[hadoop@hadoop ~]$sudo make install
切换用户
[hadoop@hadoop ~]$su - postgres
执行数据库初始化脚本
[postgres@hadoop ~]$/usr/local/pgsql/bin/initdb --encoding=utf8 -D /db/pgsql/data
The files belonging to this database system will be owned by user "postgres".
This user must also own the server process.

The database cluster will be initialized with locale "zh_CN.UTF-8".
initdb: could not find suitable text search configuration for locale "zh_CN.UTF-8"
The default text search configuration will be set to "simple".

fixing permissions on existing directory /db/pgsql/data ... ok
creating subdirectories ... ok
selecting default max_connections ... 100
selecting default shared_buffers ... 32MB
creating configuration files ... ok
creating template1 database in /db/pgsql/data/base/1 ... ok
initializing pg_authid ... ok
initializing dependencies ... ok
creating system views ... ok
loading system objects' descriptions ... ok
creating collations ... ok
creating conversions ... ok
creating dictionaries ... ok
setting privileges on built-in objects ... ok
creating information schema ... ok
loading PL/pgSQL server-side language ... ok
vacuuming database template1 ... ok
copying template1 to template0 ... ok
copying template1 to postgres ... ok

WARNING: enabling "trust" authentication for local connections
You can change this by editing pg_hba.conf or using the option -A, or
--auth-local and --auth-host, the next time you run initdb.

Success. You can now start the database server using:

    /usr/local/pgsql/bin/postgres -D /db/pgsql/data
or
    /usr/local/pgsql/bin/pg_ctl -D /db/pgsql/data -l logfile start
复制PostgreSQL执行脚本
[postgres@hadoop ~]$cp /opt/trace/software/postgresql-9.2.4/contrib/start-scripts/linux /etc/init.d/postgresql
增加执行权限
[postgres@hadoop ~]$sudo chmod +x /etc/init.d/postgresql
编辑PostgreSQL执行脚本，指定数据库文件目录
[postgres@hadoop ~]$vim /etc/init.d/postgresql
PGDATA="/db/pgsql/data"
配置可访问数据库的网络地址及端口
[postgres@hadoop ~]$vim /db/pgsql/data/postgresql.conf
listen_addresses = '*'
port = 5432                             # (change requires restart)
[postgres@hadoop ~]$sudo service postgresql start
以postgres用户登录数据库
[postgres@hadoop ~]$psql -U postgres
psql (9.2.4)
Type "help" for help.

postgres=# \l
                                  List of databases
   Name    |  Owner   | Encoding |   Collate   |    Ctype    |   Access privileges   
-----------+----------+----------+-------------+-------------+-----------------------
 postgres  | postgres | UTF8     | zh_CN.UTF-8 | zh_CN.UTF-8 | 
 template0 | postgres | UTF8     | zh_CN.UTF-8 | zh_CN.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
 template1 | postgres | UTF8     | zh_CN.UTF-8 | zh_CN.UTF-8 | =c/postgres          +
           |          |          |             |             | postgres=CTc/postgres
(3 rows)
修改postgres用户的数据库密码
postgres=# ALTER USER postgres PASSWORD '123456';
ALTER ROLE
postgres=# \q
设置密码md5验证
[postgres@hadoop ~]$vim pg_hba.conf 

local   all             all                                     md5
# IPv4 local connections:
host    all             all             127.0.0.1/32            md5
host    all             all             192.168.0.99/32         md5
# IPv6 local connections:
host    all             all             ::1/128                 md5
重启数据库服务
[postgres@hadoop ~]$sudo service postgresql restart
[sudo] password for postgres: 
Restarting PostgreSQL: ok
设置开机自动启动服务
[postgres@hadoop ~]$sudo chkconfig postgresql on
查看现存数据库：postgres-# \l
退出客户端程序psql：template1=# \q  
切换到postgre数据库:postgres-# \c postgres
查看表:template1=# \dt 
创建数据库：postgres=# create database trace_prob;
创建表：trace_prob=# create table test (id integer, name text);
插入数据：insert into test values (1,'trace_prob');
退出：postgres=# \q