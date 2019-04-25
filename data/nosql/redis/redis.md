# Redis

## 1.安装

​	官网：<https://redis.io/>  中文网：<http://redis.net.cn/>

​	1.下载[redis-3.2.5.tar.gz](https://codeload.github.com/antirez/redis/tar.gz/3.2.5)后使用xftp6将它放入我们Linux目录/opt/module/software

​		如果没有module/software目录:**mkdir -p module/software**

​	2.解压命令：tar -zxvf redis-3.2.5.tar.gz

​	3.解压完成后进入目录：cd redis-3.2.5

​	4.在redis-3.2.5目录下执行make命令

​		查看是否安装gcc：gcc -v

​			-bash：gcc：command not found（未安装）

​		运行make命令时出现故障出现的错误解析：**gcc：命名未找到**

​		能上网：yum install gcc

​     		 	yum install gcc-c++

​		Hint: It's a good idea to run 'make test'表示成功

​	5.在redis-3.2.5目录下再次执行make命令（没有安装gcc执行make）

​		Jemalloc/jemalloc.h：No such file or directory

​		解决方法：运行**make  distclean**之后再make

 	6.执行make后，跳过Redis test 继续执行make install

```shell
 make install
 make[1]: Entering directory `/redis/redis-3.0.3/src'
 
 Hint: It's a good idea to run 'make test' ;)
 
 ​    INSTALL install
 ​    INSTALL install
 ​    INSTALL install
 ​    INSTALL install
 ​    INSTALL install
 make[1]: Leaving directory `/redis/redis-3.0.3/src
```

安装成功

​	7.查看默认安装目录：/usr/local/bin  （也可以在安装时指定安装目录：make install PREFIX=/opt/module/redis）

 	8. 执行redis-server（/usr/local/bin任何目录都可以执行）

### 2.启动

​	1.备份redis.conf:拷贝一份redis.conf到其他目录

```shell
[root@linux redis]cp redis.conf /opt/module/redis/conf
```

​	2.启动Redis：执行redis.server命令脚本

```shell
[root@linux redis]bin/redis-server /opt/module/redis/conf/redis.conf
```

​		默认启动的Redis服务为用户服务，也就意味着，Redis会将启动信息打	印在控制台窗口中，如果关闭这个窗口，redis服务也就关闭了

​		如果将redis.conf文件里面的daemonize no 改成yes，就可以让redis服务	在后台启动

​		redis-server /usr/local/bin/redis/redis.conf

​		查看redis进程信息：**ps -ef|grep redis**

​	3.用客户端访问：redis-cli（多个端口可以redis-cli -p 6379）

​	4.测试验证：ping(pong 成功)

​	5.关闭r单实例edis：**redis-cli shutdown** 或者在客户端输入**shutdown**命令，多实例关闭，指定端口关闭：**redis-cli -p 6379 shutdown**

​	6.redis默认16个数据库，类似数组下标从0开始，初始默认使用0号库

​		使用命令select  <dbid> 来切换数据库 如：select 3

​		统一密码管理，所有库都是同一密码，要么都ok要么一个也连不上

### 3.数据类型

1. string（一个key对应一个value，string类型是二进制安全的，意味着可以存储任何数据，如图片或者序列化的对象）

   **get <key>** 				 :查询对应键值

   **set <key> <value>** 		:添加键值对

   **append <key> <value>**	 :将给定的<value>追加

   **strlen <key>**  			  :获取值的长度

   **setnx <key> <value>** 	   :只有在key不存在是设置key的值

   **incr <key>**				:将key中存储的数字值增1，只能对数字操作，如果为空，新增值为1

   **decr <key>**				:将key中存储的数字值减1，只能对数字操作，如果为空，新增值为-1

   **incrby/decrby <key> <步长>**：将key中存储的数字值增减，自定义步长

   **mset <key1> <value1> <key2> <value2>... ** :同时设置一个或多个key-value对（m代表multi）

   **mget <key1> <key2> <key3> ... ** :同时获取一个或多个value

   **msetnx <key1> <value1> <key2> <value2>... ** :同时设置一个或多个key-value对，当且仅当所有给定可以都不存在（**只要一个key存在，所有失败**）

   **getrange <key> <起始位置> <结束位置>** ：获得值的范围，类似java中的substring    getrange key1 0 -1 获取整个字符串，-1代表倒数第一个

   **setrange <key> <起始位置> <value>**：用<value>覆写<key>所存储的字符串值，从<其实位置>开始

   **setex <key> <过期时间> <value>**:设置键值的同时设置过期时间，单位秒

   **getset <key> <value>**  以新换旧，设置了新值同时获得旧值

2. set

3. list

4. hash

5. zset（可排序）

### 4.命令

​	**keys  ***   ：查询当前库的所有键

​	**exists  <key>** :判断某个键是否存在

​	**type  <key>** :查看键的类型

​	**del  <key>** :删除某个键

​	**expire <key> <seconds>**:为键值设置过期时间，单位秒

​	**ttl <key>** :查看还有多少秒过期，-1表示永不过期，-2表示已过期

​	**dbsize** ：查看当前数据库的key的数量

​	**flushdb**：清空当前库

​	**flushall**：清空所有库

