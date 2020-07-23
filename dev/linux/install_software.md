[Ubuntu下常用软件安装方法](https://blog.csdn.net/oppo62258801/article/details/78866464)

[在Ubuntu上安装Intellij IDEA并创建桌面快捷方式](https://www.cnblogs.com/zaid/p/11141348.html)

安装vim ：sudo apt install vim
安装git ：apt install git
安装svn ：sudo apt-get install subversion

[Windows10远程桌面Ubuntu优麒麟](https://my.oschina.net/chipo/blog/3111230)

[FileZilla工具window10与Ubuntu之间的文件传输](https://blog.csdn.net/songyunli1111/article/details/79792958)

[liunx 系统 （ubuntu(优麒麟)）如何 配置JDK的方法](https://blog.csdn.net/qq_33716443/article/details/51760650)


###1.Chrome

- 32位
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_i386.deb
    sudo dpkg -i google-chrome-stable_current_i386.deb
```
- 64位
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
    sudo dpkg -i google-chrome-stable_current_amd64.deb 
```
```shell
    wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb
    sudo dpkg -i google-chrome*
    sudo apt-get -f install
```
