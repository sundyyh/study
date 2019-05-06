##  关机&重启命令

​	shutdown -h now  	表示立即关机

​	shutdown  -h 1		一分钟后关机

​	shutdown  -r		立即重启

​	halt	直接使用，等效于关机

​	reboot	现在重启计算机

​	sync	把内存的数据同步到磁盘

​	注意：当我们关机或者重启是，都应该先执行以下sync命令，把内存的数据写入磁盘，防止数据丢失

## 用户登录和注销

​	登录时尽量少的用root账号登录，因为它是系统管理员，最大的权限，避免操作失误，可以利用普通用户登录，登录后再用“**su** -用户名”命令来切换成系统管理员

​	在提示符下输入logout即可注销用户

​	**logout**   退出（logout在图像运行级别无效）

## 用户管理

​	说明：

​		1.Linux系统是一个多用户多任务的操作系统，任何一个要使用系统资源的用户，都必选首先向系统管理员申请一个账号，然后以这个账号的身份进入系统

​		2.Linux的用户至少属于一个组

​	**添加用户**

```shell
$ useradd [选项] 用户名 #中括号可以不写
```

​	user sundy：会创建一个和sundy同名的组，并将sundy添加到sundy组中，同时会自动创建和用户名同名的家目录，可以通过useradd -d 指定目录 新的用户名，给新创建的用户指定家目录

​	**指定或修改密码**：

```shell
$ passwd 用户名
```

​	**删除用户**

```shell
userdel 用户名 #该方法会保留家目录
userdel -r 用户名 #同时删除家目录
```

​	**查询用户信息**

```shell
$ id 用户名
[root@localhost home]# id sundy
uid=1000(sundy) gid=1000(sundy) groups=1000(sundy)

```

uid=用户id号 ；gid=所在组的id号；groups=组名称

​	**切换用户**

```shell
$ su - 用户名 （-可以不用写）
```

​	返回到原来用户：**exit**

## 用户组

​	介绍：类似于角色，系统可以对有共性的多个用户进行统一的管理

​	**添加组**

```shell
groupadd 组名 #添加组
groupdel 组名 #删除组
```

​	**添加用户时直接添加上组**

```shell
$ useradd -g 用户组 用户名
```

​	**修改用户组**

```shell
$ usermod -g 用户组 用户名
```

**用户和组的相关文件**

​	**/etc/passwd文件**：用户（user）的配置文件，记录用户的各种信息

​		每行的含义->用户名：口令：用户标识号：组标识号：注释性描述：主目录：登录Shell

​	/etc/shadow:口令的配置文件（密码和登录信息，是加密）

​		每行含义->登录名：加密口令：最后一次修改时间：最小时间间隔：最大时间间隔：警告时间：不活动时间：失效时间：标志

​	**/etc/group文件**：组（group）的配置文件，记录linux包含的组信息

​		每行含义->组名：口令：组标识号：组内用户列表

#### 指定运行级别

0： 关机

1： 单用户【找回丢失密码,因为该级别不需要密码进入】

2： 多用户状态无网络服务

**3： 多用户状态有网络服务  命令行模式**

4： 系统未使用保留给用户

5： GUI（图形桌面 模式）

6 ： 重启

​	常用运行级别是3和5，要修改默认的运行级别可修改文件：/etc/inittab的 **id：5：initdefault**

​	**查看运行级别**

​	1.who -r ： 显示当前运行级别以及系统当前时间 

​	2.runlevel ： 显示前一个运行级别（无则显示”N”） 、 当前运行级别

```shell
[sundy@localhost ~]$ who -r
         run-level 3  2019-04-30 01:50
[sundy@localhost ~]$ runlevel
N 3
```

​	切换运行级别

```shell
$ init 3  #[0123456]
```

​	案例：通过init来切换到不同级别，比如从5切换到3，然后关机

​		①init 3 ；②init 5；③init 0

**如何找回root密码**（进入到单用户模式，然后修改密码，因为单用户模式root不需要密码）

​	1.首先，打开centos7，在选择进入系统的界面按“e”进入编辑页面

​	2.然后按向下键，找到以“Linux16”开头的行，在该行的最后面输入“init=/bin/sh”

​	3.接下来按“ctrl+X”组合键进入单用户模式

#### 帮助命令

​	**man** 获得帮助信息

​	语法：man [命令或配置文件] （功能描述：获取帮助信息）

```shell
$ man ls #获取ls命令的帮助信息
```

​	**help** 指令

​		语法：help 命令 （功能描述：获取**shell**内置命令的帮助信息）

```shell
$ help cd
```

#### 文件目录类

###### 	pwd指令

​		基本语法：pwd	（功能描述：显示当前工作目录的绝对路径Print the name of the current working directory）

##### ls指令

​	语法： ls [选项]	[目录或文件]

​	常用参数

​		-a：显示当前目录所有的文件和目录，包括隐藏的（all）

​		-l：以列表的方式显示信息（use a long listing format）

```shell
[sundy@localhost ~]$ ls -l
total 1
-rw-rw-r--. 1 sundy sundy 326 Apr 29 22:39 Test.java
```

#### 	cd命令

​		语法：cd [参数]	（功能描述：切换到指定目录）

​		常用参数

​		**cd ~**  或**cd**     回到自己的家目录

​		**cd ..**       回到当前目录的上一级目录

​		**绝对路径**：即从根目录（/）开始定位

​		**相对路径**：从当前目录开始定位到需要的目录去

​		如当前目录为/root，去/home目录可使用 ../home或者/home

```shell
$ cd ../../root
$ cd /home/sundy
$ cd #回家目录
$ cd ~ #回家目录
$ cd .. #上级目录
```

#### mkdir（make directories创建目录）

​	语法：mkdir 【选项】 要创建的目录

​	常用选项

​		-p ：创建多级目录（parents）

```shell
$ mkdir -p a/b/c
```

#### rmdir(remove empty directories)

​	语法：rmdir 【选项】要清除的空目录（目录下有内容时无法删除）

```shell
$ rmdir dog
```

​	**删除非空目录使用rm -rf /home/dog**

#### touch指令（创建空文件）

​	语法：touch 文件名称

```shell
$ touch hello.txt
 touch 1.txt 2.txt  #同时创建两个文件
```

#### cp指令（copy files and directories）

​	语法：**cp 【选项】 source dest**

​	常用选项

​		-r：递归复制整个文件夹 --recursive【copy directories recursively】

```shell
$ cp aaa.txt bbb/ #将当前目录下aaa.txt文件拷贝到当前面目录的bbb这个目录下，有相同文件会提示是否覆盖
$ cp -r test/ dog/
$ \cp -r test/ dog/ #第二次copy，会提示是否覆盖，使用反斜杠 \强制覆盖不提示
```

#### rm指令（remove files or directories）

​	语法：rm 【选项】 要删除的文件或目录

​	常用选项

​		-r：递归删除整个文件夹

		-f：强制删除不提示（--force； ignore nonexistent files and arguments, never prompt）
```shell
$ rm hello.txt
$ rm -rf  bbb/ #删除目录

```

#### mv指令（移动文件与目录或重命名）

​	语法：

​		mv oldNameFile newNameFile（重命名）

​		mv /temp/movefile /targetFolder(移动文件)

```shell
$ mv aaa.txt bbb.txt #重命名
$ mv a.txt /home/sundy #将当前目录a.txt文件移动到...
```

#### cat 指令（concatenate files and print on the standard output只读方式查看文件内容）

​	语法：cat 【选项】 要查看的文件

​	常用选项

​		-n ：显示行号

```shell
$ cat -n /etc/profile
$ cat -n /etc/profile | more #|管道命令加more 分页显示
```

#### more指令

​	more指令是一个基于VI编辑器的文本过滤器，它以全屏幕的方式按页显示文本文件的内容，more命令中内置了若干快捷键

​	语法：more 要查看的文件

| 操作            | 功能说明                         |
| --------------- | -------------------------------- |
| 空格键（space） | 向下翻一页                       |
| Enter           | 向下翻（一行）                   |
| q               | 立刻离开more，不再显示该文件内容 |
| ctrl+F          | 向下翻动一屏                     |
| ctrl+B          | 返回上一屏                       |
| =               | 输出当前的行号                   |
| ：f             | 输出文件名和当前行号             |

##### less指令

​	less指令用来分屏查看文件内容，它和more指令相似，但是比more指令更加强大，支持各种显示终端。less指令在显示文件内容时，并不是一次将整个文件加载之后才显示，而是根据显示需要加载内容，**对于显示大型文件具有较高的销量**

​	语法：less 要查看的文件

| 操作            | 功能说明                                         |
| --------------- | ------------------------------------------------ |
| 空格键（space） | 向下翻一页                                       |
| Enter           | 向下翻（一行）             	                     |
| [pagedown]      | 向下翻一页                                       |
| [pageup]        | 向上翻一页                                       |
| /字串           | 向下搜寻【字串】的功能，n：向下查找；N：向上查找 |
| ?字串           | 向上搜寻【字串】的功能，n：向下查找；N：向上查找 |
| q               | 离开less这个程序                                 |

##### >指令和>>指令

​	**>**输出重定向（会将原来的文件的内容覆盖）和**>>**追加（不会覆盖原来文件的内容，而是追加到文件的尾部）

​	基本语法

​	

| 语法              | 功能描述                        |
| ----------------- | ------------------------------- |
| ls -l >文件       | 列表的内容写入文件a.txt(覆盖写) |
| ls -al >>文件     | 列表的内容追加到文件a.txt的末尾 |
| cat 文件1>文件2   | 将文件1的内容覆盖到文件2        |
| echo "内容">>文件 |                                 |

```shell
$ ls -l > a.txt #将ls -l的显示的内容覆盖写入到a.txt文件，如果a.txt不存在就创建该文件
$ ls -al >>b.txt #将ls -al显示的内容追加到后面的文件中去
$ cal >>/home/mycal #将当前日历信息追加到/home/mycal文件中
```

#### echo指令

​	echo输出内容到控制台

​	语法：echo  [选项] [输出内容]

```shell
$ echo $PATH
/usr/local/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/home/sundy/.local/bin:/home/sundy/bin

$ echo $PATH #输出环境变量
$ echo "Hello,world"#输出Hello,world
```

##### head指令

​	head用于显示文件的开头部分内容，默认情况head指令显示文件的前10行内容

​	语法：**head 文件** (查看文件头10行内容)

​	 	  **head -n 5 文件** (查看文件头5行内容，5可以是任意行数)

##### tail指令

tail用于输出文件中尾部的内容，默认情况下tail指令显示文件的前10行内容。
• 基本语法
	1) tail 文件 （功能描述：查看文件头10行内容）
	2) tail -n 5 文件 （功能描述：查看文件头5行内容， 5可以是任意行数）
	**3) tail -f 文件** （功能描述：实时追踪该文档的所有更新，**常用**）
• 应用实例
案例1: 查看/etc/profile 最后5行的代码
案例2: 实时监控 mydate.txt , 看看到文件有变化时，是否看到， 实时的追加日期 

```shell
$ tail -f mydate.txt
```

##### ln 指令

​	软链接也成为符号链接，类似于**windows里的快捷方式**， 主要存放了链接其他文件的路径
​	• 基本语法
​		ln -s [原文件或目录] [软链接名] （功能描述：给原文件创建一个软链接）
​	• 应用实例
​		案例1: 在/home 目录下创建一个软连接 linkToRoot，连接到 /root 目录
​		案例2: 删除软连接 linkToRoot 

```shell
[root@localhost home]$  ln -s /root linkToRoot
lrwxrwxrwx. l root root 6 3月   7 01：28 linkToRoot -> /root/
$ rm -rf linkToRoot #不要带斜杠,如linkToRoot/
```

​	• 细节说明
​		当我们使用pwd指令查看目录时，仍然看到的是软链接所在目录。 

#### history指令

​	查看已经执行过历史命令,也可以执行历史指令
​		• 基本语法
​			history （功能描述：查看已经执行过历史命令）
​		• 应用实例
​			案例1: 显示所有的历史命令
​			案例2: 显示最近使用过的10个指令。
​			案例3：执行历史编号为5的指令 

```shell
$ history
$ history 10
$ !5 #执行历史编号为5的指令
```

##### date指令-显示当前日期

• 基本语法
	1) date （功能描述：显示当前时间）
	2) date +%Y （功能描述：显示当前年份）
	3) date +%m （功能描述：显示当前月份）
	4) date +%d （功能描述：显示当前是哪一天）
	5) date "+%Y-%m-%d %H:%M:%S"（功能描述：显示年月日时分秒）
• 应用实例
	案例1: 显示当前时间信息
	案例2: 显示当前时间年月日
	案例3: 显示当前时间年月日时分秒 

```shell
[root@localhost home]$ date +%Y
2019
[root@localhost home]$ date +%m
05
[root@localhost home]$ date +%d
06
[root@localhost home]$ date "+%Y-%m-%d %H:%M:%S"    #+号不能少
2019-05-06 16:20:49
```

date指令-设置日期
	• 基本语法
		date -s 字符串时间
	• 应用实例
		案例1: 设置系统当前时间 ， 比如设置成 2019-05-06 15:35:40

```shell
[root@localhost home]$ date -s "2019-05-06 15:35:40"
Mon May  6 15:35:40 EDT 2019
```

##### cal指令

​	查看日历指令
​	• 基本语法
​		cal [选项] （功能描述：不加选项，显示本月日历）
​	• 应用实例
​		案例1: 显示当前日历
​		案例2: 显示2020年日历 

```shell
[root@localhost home]$ cal 
      May 2019      
Su Mo Tu We Th Fr Sa
          1  2  3  4
 5  6  7  8  9 10 11
12 13 14 15 16 17 18
19 20 21 22 23 24 25
26 27 28 29 30 31
[root@localhost home]# cal 2020
                               2020                               

       January               February                 March       
Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa
          1  2  3  4                      1    1  2  3  4  5  6  7
 5  6  7  8  9 10 11    2  3  4  5  6  7  8    8  9 10 11 12 13 14
12 13 14 15 16 17 18    9 10 11 12 13 14 15   15 16 17 18 19 20 21
19 20 21 22 23 24 25   16 17 18 19 20 21 22   22 23 24 25 26 27 28
26 27 28 29 30 31      23 24 25 26 27 28 29   29 30 31

        April                   May                   June        
Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa
          1  2  3  4                   1  2       1  2  3  4  5  6
 5  6  7  8  9 10 11    3  4  5  6  7  8  9    7  8  9 10 11 12 13
12 13 14 15 16 17 18   10 11 12 13 14 15 16   14 15 16 17 18 19 20
19 20 21 22 23 24 25   17 18 19 20 21 22 23   21 22 23 24 25 26 27
26 27 28 29 30         24 25 26 27 28 29 30   28 29 30
                       31
        July                  August                September     
Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa
          1  2  3  4                      1          1  2  3  4  5
 5  6  7  8  9 10 11    2  3  4  5  6  7  8    6  7  8  9 10 11 12
12 13 14 15 16 17 18    9 10 11 12 13 14 15   13 14 15 16 17 18 19
19 20 21 22 23 24 25   16 17 18 19 20 21 22   20 21 22 23 24 25 26
26 27 28 29 30 31      23 24 25 26 27 28 29   27 28 29 30
                       30 31
       October               November               December      
Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa   Su Mo Tu We Th Fr Sa
             1  2  3    1  2  3  4  5  6  7          1  2  3  4  5
 4  5  6  7  8  9 10    8  9 10 11 12 13 14    6  7  8  9 10 11 12
11 12 13 14 15 16 17   15 16 17 18 19 20 21   13 14 15 16 17 18 19
18 19 20 21 22 23 24   22 23 24 25 26 27 28   20 21 22 23 24 25 26
25 26 27 28 29 30 31   29 30                  27 28 29 30 31


```

##  搜索查找类

##### find指令

​	find指令将从指定目录向下递归地遍历其各个子目录，将满足条件的**文件或者目录**显示在终
端。
​	• 基本语法
​		find [搜索范围，即搜索目录] [选项]
​	• 选项说明

| 选项            | 功能                             |
| --------------- | -------------------------------- |
| -name<查询方式> | 按照指定的文件名查找模式查找文件 |
| -user<用户名>   | 查找属于指定用户名所有文件       |
| -size<文件大小> | 按照指定的文件大小查找文件       |

​	• 应用实例
​		案例1: 按文件名：根据名称查找/home 目录下的hello.txt文件
​		案例2： 按拥有者：查找/opt目录下，用户名称为 nobody的文件
​		案例3： 查找整个linux系统下大于200m的文件（+n 大于 -n小于 n等于）		案例4： 查询/目录下，所有.txt的文件

```shell
$ find /home -name hello.txt 
/home/hello.txt
$ find /opt -user nobody
$ find / -size +200M
$ find / -size -200M
$ find / -size 200M
$ find / -name *.txt
```

##### locate指令

​	locaate指令可以快速定位文件路径。 locate指令利用事先建立的系统中所有文件名称及路径的locate数据库实现快速定位给定的文件。 Locate指令无需遍历整个文件系统，查询速度较快。
​	为了保证查询结果的准确度，管理员必须定期更新locate时刻。
• 基本语法
​	locate 搜索文件
• 特别说明
​	由于locate指令基于数据库进行查询，**所以第一次运行前，必须使用updatedb指令创建locate数据库**。
• 应用实例
​	案例1: 请使用locate 指令快速定位 hello.txt 文件所在目录 

• 注意

​	updatedb提示command not found  使用**yum install mlocate**安装



```shell
$ [root@localhost home]$ updatedb
bash: updatedb: command not found
[root@localhost home]$ yum install mlocate
[root@localhost home]# updatedb
[root@localhost home]# locate hello.txt
/home/hello.txt

```

#### grep指令和 管道符号 |

​	grep 过滤查找 ， 管道符， “|”，表示将前一个命令的处理结果输出传递给后面的命令处理。
• 基本语法
​	grep [选项] 查找内容 源文件
• 常用选项 	

| 选项 | 功能             |
| ---- | ---------------- |
| -n   | 显示匹配行及行号 |
| -i   | 忽略字母大小写   |

• 应用实例
案例1: 请在 hello.txt 文件中，查找 "yes" 所在行，并且显示行号 

```shell
$ cat hello.txt | grep yes
yes
yes
$ cat hello.txt | grep -n yes
4:yes
7:yes
```

## 压缩和解压类 

##### gzip/gunzip 指令(很少用)

​	gzip 用于压缩文件， gunzip 用于解压的
​	• 基本语法
​		gzip 文件 （功能描述：压缩文件，只能将文件压缩为*.gz文件）
​		gunzip 文件.gz （功能描述：解压缩文件命令）
​	• 应用实例
​		案例1: gzip压缩， 将 /home下的 hello.txt文件进行压缩
​		案例2: gunzip压缩， 将 /home下的 hello.txt.gz 文件进行解压缩 

```shell
$ gzip hello.txt #压缩完生成hello.txt.gz文件后会删除原来文件	
$ gunzip hello.txt.gz	#
```

##### zip/unzip 指令

​	zip 用于压缩文件， unzip 用于解压的，这个在项目打包发布中很有用的
• 基本语法
​	zip [选项] XXX.zip 将要压缩的内容（功能描述：压缩文件和目录的命令）
​	unzip [选项] XXX.zip （功能描述：解压缩文件）
• zip常用选项
​	**-r**：递归压缩，即压缩目录
​	• unzip的常用选项
​	**-d**<目录> ： 指定解压后文件的存放目录
• 应用实例
​	案例1: 将 /home下的 所有文件进行压缩成 mypackage.zip
​	案例2: 将 mypackge.zip 解压到 /opt/tmp 目录下 

```shell
$ zip -r mypackage.zip /home/
$ unzip -d /opt/tmp/ mypackage.zip
```

##### tar 指令

​	tar 指令 是打包指令，最后打包后的文件是 .tar.gz 的文件。
​	• 基本语法
​		tar [选项] XXX.tar.gz 打包的内容 (功能描述：打包目录，压缩后的文件格式.tar.gz)
​	• 选项说明 

| 选项 | 功能               |
| ---- | ------------------ |
| -c   | 产生.tar打包文件   |
| -v   | 显示详细信息       |
| -f   | 指定压缩后的文件名 |
| -z   | 打包同时压缩       |
| -x   | 解包.tar文件       |

• 应用实例
	案例1: 压缩多个文件，将 /home/a1.txt 和 /home/a2.txt 压缩成 a.tar.gz
	案例2: 将/home 的文件夹 压缩成 myhome.tar.gz
	案例3: 将 a 解压到当前目录
	案例4: 将myhome.tar.gz  解压到/opt/tmp2目录下
	

 

```shell
$ tar -zcvf a.tar.gz a1.txt a2.txt  # 打包后文件名为a.tar.gz
$ tar -zcvf myhome.tar.gz /home/
$ tar -zxvf a.tar.gz
$ tar -zxvf a.tar.gz -C /opt/tmp2/ #解压目录要存在，否则报错
```

## 组管理和权限管理 

#### 	Linux组基本介绍

​		在linux中的每个用户必须属于一个组，不能独立于组外。在linux中每个文件有所有者、所在组、其它组的概念。
​		1) 所有者
​		2) 所在组
​		3) 其它组
​		4) 改变用户所在的组 

​		 文件/目录 所有者：一般为文件的创建者,谁创建了该文件， 就自然的成为该文件的所有者 

#### 	查看文件的所有者 

​		1) 指令： ls -ahl
​		2) 应用实例：创建一个组police，再 创建一个用户tom，将tom放在police组，然后使用tom来创建一个文件ok.txt,看看情况如何
​		

```shell
$ groupadd police
$ useradd -g police tom
[tom@localhost ~]$ touch ok.txt
[tom@localhost ~]$ ls -ahl
-rw-rw-r--. 1 tom police    0 May  6 16:45 ok.txt
# tom为所有者 police为文件所在组（一般为用户所在组，可以改）
```

#### 	修改文件所有者

​		• 指令： chown 用户名 文件名
​		• 指令： chown newower:newgroup file 改变文件的所有者和所有组
​		• **-R** 如果是目录 则使其下所有子文件或目录递归生效
​		• 应用案例
​			1)使用root 创建一个文件apple.txt ，然后将其所有者修改成 zhangfei 	
​			2)请将 /home/abc .txt 文件的所有者修改成 tom
​			3)请将 /home/kkk 目录下所有的文件和目录的所有者都修改成tom 	

```shell
$ chown tom apple.txt
$ chown tom abc.txt
$ chown -R tom kkk/
```

#### 	组的创建

​		基本指令:groupadd 组名 

​		应用实例:
​			创建一个组, ,monster
​			创建一个用户 fox ，并放入到 monster组中 		

```shell
$ groupadd monster
$ useradd -g monster fox
$ id fox
```

#### 	文件/目录 所在组

​		当某个用户创建了一个文件后，这个文件的所在组就是该用户所在的组。
​	查看文件/目录所在组
​	• 基本指令
​		ls –ahl

#### 	修改文件所在的组

​	• 基本指令
​		chgrp 组名 文件名
​	• 应用实例
​		1).使用root用户创建文件 orange.txt ,看看当前这个文件属于哪个组，然后将这个文件所在组，修改到 fruit组。 

​		2).请将 /home/kkk 目录下所有的文件和目录的所在组都修改成bandit(土匪) 

```shell
$ touch orange.txt
$ ls -l
-rw-r--r--. 1 root root      0 May  6 16:45 orange.txt
$ chgrp police orange.txt
$ ls -l
-rw-r--r--. 1 root police    0 May  6 16:45 orange.txt
$ chgrp -R bandit  kkk/
```

#### 	其它组

​		除文件的所有者和所在组的用户外，系统的其它用户都是文件的其它组。

#### 	 改变用户所在组

​	在添加用户时，可以指定将该用户添加到哪个组中，同样的用root的管理权限可以改变某个用户所在的组。

​	用法：

​	1) usermod –g 组名 用户名
​	2) usermod –d 目录名 用户名 改变该用户登陆的初始目录。
​	应用实例
​		将 zwj 这个用户从原来所在组，修改到 wudang 组。 

```shell
$ usermod -g wudang zwj
```

#### 权限的基本介绍

ls -l 中显示的内容如下：
-rwxrw-r-- 1 root root 1213 Feb 2 09:39 abc
0-9位说明
1) 第0位确定文件类型(**d**【目录】, **-**【普通文件】 , l 【链接文件，软链接】, c【字符设备，如键盘、鼠标】 , b【块文件，如硬盘】)
2) 第1-3位确定所有者（该文件的所有者）拥有该文件的权限。 ---User
3) 第4-6位确定所属组（同用户组的）拥有该文件的权限， ---Group
4) 第7-9位确定其他组的用户拥有该文件的权限 ---Other 

##### rwx权限详解

**rwx作用到文件**
	1) [ r ]代表可读(read): 可以读取,查看
	2) [ w ]代表可写(write): 可以修改,但是不代表可以删除该文件,删除一个文件的前提条件是对该文件所在的目录有写权限，才能删除该文件.
	3) [ x ]代表可执行(execute):可以被执行
**rwx作用到目录**
	1) [ r ]代表可读(read): 可以读取， ls查看目录内容
	2) [ w ]代表可写(write): 可以修改,目录内创建+删除+重命名目录
	3) [ x ]代表可执行(execute):可以进入该目录 

```shell
-rw-r--r--. 1 root police  0  May  6 16:45 orange.txt
|           | 	|	 |     |
|           |   | 	 |     |文件大小，如果是目录4096
|           |	|	 |文件或目录所在组   
|           |   |
|           |   |文件或目录所有者
|           |					
|           如果是文件表示硬链接的数，如果是目录则表示改目录的子目录的个数
|文件类型
```

**ls -l 中显示的内容如下：**
	-rwxrw-r-- 1 root root 1213 Feb 2 09:39 abc
	10个字符确定不同用户能对文件干什么
	第一个字符代表文件类型： 文件 (-),目录(d),链接(l)
	其余字符每3个一组(rwx) 读(r) 写(w) 执行(x)
	第一组rwx : 文件拥有者的权限是读、写和执行
	第二组rw- : 与文件拥有者同一组的用户的权限是读、写但不能执行
	第三组r-- : 不与文件拥有者同组的其他用户的权限是读不能写和执行
	**可用数字表示为: r=4,w=2,x=1 因此rwx=4+2+1=7**
	1 文件：硬连接数或 目录：子目录数
	root 用户
	root 组
	1213 文件大小(字节)， 如果是文件夹，显示 4096字节
	Feb 2 09:39 最后修改日期
	abc 文件名 

#### 修改权限-chmod

基本说明：通过chmod指令，可以修改文件或者目录的权限。
第一种方式： **+ 、 -、 = 变更权限**
**u:所有者 g:所有组 o:其他人 a:所有人(u、 g、 o的总和)**
	1) chmod u=rwx,g=rx,o=x 文件目录名	
	2) chmod o+w 文件目录名 (给其它组添加写权限)
	3) chmod a-x 文件目录名 （给所有组减执行权限）
• 案例演示
	1) 给abc文件 的所有者读写执行的权限，给所在组读执行权限，给其它组读执行权限。
	2) 给abc文件的所有者除去执行的权限，增加组写的权限
	3) 给abc文件的所有用户添加读的权限 	
	4) 将 /home/abc.txt 文件的权限修改成 rwxr-xr-x, 使用给数字的方式实现 

第二种方式：**通过数字变更权限**
r=4, w=2, x=1 rwx=4+2+1=7
chmod u=rwx,g=rx,o=x 文件目录名
相当于 chmod 751 文件目录名 

```shell
$ chmod u=rxw,g=rx,o=rx abc
$ chmod a-x,g+w abc
$ chmod a+r abc
$ chmod 755 /home/abc.txt
```

