### [Linux环境变量配置全攻略](https://www.cnblogs.com/youyoui/p/10680329.html)

#### 方法一：export PATH

使用<kbd>export</kbd>命令直接修改<kbd>PATH</kbd>的值，配置MySQL进入环境变量的方法
```jshelllanguage
export PATH=/home/sundy/mysql/bin:$PATH

# 或者把PATH放在前面
export PATH=$PATH:/home/sundy/mysql/bin
```
注意事项：

- 生效时间：立即生效
- 生效期限：当前终端有效，窗口关闭后无效
- 生效范围：仅对当前用户有效
- 配置的环境变量中不要忘了加上原来的配置，即$PATH部分，避免覆盖原来配置

#### Linux环境变量配置方法二：vim ~/.bashrc

通过修改用户目录下的~/.bashrc文件进行配置：
```jshelllanguage
vim ~/.bashrc

# for java 
export JAVA_HOME=/home/cmm/jdk
export CLASSPATH=$CLASSPATH:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
export PATH=$PATH:$JAVA_HOME/bin:$JAVA_HOME/jre/bin:/home/cmm/android-sdk-linux/tools:/home/cmm/android-sdk-linux/platform-tools
export ANDROID_SDK_HOME=/home/cmm/avds

export PATH="/home/linuxbrew/.linuxbrew/bin:$PATH"
export MANPATH="/home/linuxbrew/.linuxbrew/share/man:$MANPATH"
export INFOPATH="/home/linuxbrew/.linuxbrew/share/info:$INFOPATH"

#for gradle
export GRADLE_HOME=/home/cmm/.gradle/wrapper/dists/gradle-5.1-bin/djumv4jisrix1ndp1lfpceh59/gradle-5.1
export PATH=$GRADLE_HOME/bin:$PATH

```
注意事项：

- 生效时间：使用相同的用户打开新的终端时生效，或者手动source ~/.bashrc生效
- 生效期限：永久有效
- 生效范围：仅对当前用户有效
- 如果有后续的环境变量加载文件覆盖了PATH定义，则可能不生效