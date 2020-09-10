[参考来源](https://blog.csdn.net/ausboyue/article/details/105224700)

阿里私服    https://maven.aliyun.com/mvn/guide


###1.下载 
    http://download.sonatype.com/nexus/3/nexus-3.22.1-02-unix.tar.gz
###2.修改配置
修改nexus用户为root： vim bin/nexus.rc      =>     run_as_user="root"

注：

可以修改默认端口：vim etc/nexus-default.properties  =>  application-port=8081

可以修改数据、日志存储位置：vim bin/nexus.vmoptions
###3 启动
>nexus run

访问界面：http://localhost:8081

默认用户名：admin

默认密码：admin123

就可以看到提示信息：
Unable to delete file: /usr/local/sonatype-work/nexus3/cache/bundle129/bundle.info
Unable to update instance pid: /usr/local/sonatype-work/nexus3/instances/instance.properties (权限不够)

ok，我们根据查找到的目录知道要更改两个目录的权限：
chown -R sundy:sundy nexus-3.14.0-04/ sonatype-work/

###获取密码
```
/usr/local/lib/sonatype-work/nexus3$ cat admin.password
```
用户名为admin

###新建仓库
[代理详细信息](https://maven.aliyun.com/mvn/guide)
    
Repositories->Create repository->maven2(proxy)->填写名称如：google-repo->填写代理地址
  ```jshelllanguage
aliyun-repo            https://maven.aliyun.com/repository/public
google-repo            https://maven.aliyun.com/repository/google
gradle-plugin-repo     https://maven.aliyun.com/repository/gradle-plugin
holder-repo            http://nexus.holderzone.cn/nexus/content/repositories/snapshots/
jitpack-repo           https://jitpack.io
kotlin-repo            https://kotlin.bintray.com/kotlinx
```

###创建代理maven仓库组
新建仓库，选择maven2 (group)，选择创建的代理



###设置开机启动
1.添加入口程序的软连接到/etc/init.d/目录下：
ln -s /opt/nexus-2.10.0-02/bin/nexus /etc/init.d/nexus

2.修改nexus权限：

chmod 755 /etc/init.d/nexus

3.使用update-rc.d工具以默认的优先级在启动之前添加nexus服务：

update-rc.d nexus defaults
sudo service nexus start
4.关机重启，验证OK。

```
sundy@sundy-B250-HD3:~$ sudo ln -s /usr/local/lib/nexus-3.22.1-02/bin/nexus /etc/init.d/nexus
sundy@sundy-B250-HD3:~$ chmod 755 /etc/init.d/nexus
sundy@sundy-B250-HD3:~$ update-rc.d nexus defaults
sundy@sundy-B250-HD3:~$ sudo service nexus start
```
