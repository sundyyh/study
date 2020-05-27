
安装svn ：sudo apt-get install subversion

### 将文件checkout到本地目录
```shell
svn checkout path
或
svn co
```
1. svn co http://路径(目录或文件的全路径)　[本地目录全路径] --username 用户名--password 密码
2. svn co svn://路径(目录或文件的全路径)　[本地目录全路径] --username 用户名--password 密码
3.svn checkout http://路径(目录或文件的全路径)　[本地目录全路径] --username　用户名
4. svn checkout svn://路径(目录或文件的全路径)　[本地目录全路径] --username　用户名

### 提交
```shell
    svn add 文件名
    // 添加test.php 
    svn add test.php
    //添加当前目录下所有的php文件
    svn add *.php 
    svn commit -m 
    //     
    svn commit -m "添加我的测试用全部php文件" *.php
    //注意这个*表示全部文件
    svn commint -m "备注" * 
```
告诉SVN服务器要添加文件了，还要用svn commit -m真实的上传上去

### 更新
```sh
//后面没有目录，默认将当前目录以及子目录下的所有文件都更新到最新版本
svn update 
//更新与版本库同步
svn update test.c
```