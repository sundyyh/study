###1.下载 
    http://download.sonatype.com/nexus/3/nexus-3.22.1-02-unix.tar.gz
###2.修改配置
修改nexus用户为root： vim bin/nexus.rc      =>     run_as_user="root"

注：

可以修改默认端口：vim etc/nexus-default.properties  =>  application-port=8081

可以修改数据、日志存储位置：vim bin/nexus.vmoptions
###3 启动
访问界面：http://localhost:8081

默认用户名：admin

默认密码：admin123

>nexus run

就可以看到提示信息：
Unable to delete file: /usr/local/sonatype-work/nexus3/cache/bundle129/bundle.info
Unable to update instance pid: /usr/local/sonatype-work/nexus3/instances/instance.properties (权限不够)

ok，我们根据查找到的目录知道要更改两个目录的权限：
chown -R sundy:sundy nexus-3.14.0-04/ sonatype-work/

###获取密码
/usr/local/lib/sonatype-work/nexus3$ cat admin.password
用户名为admin