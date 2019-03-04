##1、gradle插件版本配置位置：

project对应的build.gradle文件中

```text
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:1.2.3'
    }
}
```

## 2、gradle版本配置位置：

gradle-wrapper.properties 中 

```text
distributionUrl=https\://services.gradle.org/distributions/gradle-2.2-all.zip
```

具体版本对应如下：

  |目录|功能|
 | ------ | ------: |
 | Plugin version | Required Gradle version |
 |  1.0.0 - 1.1.3   | 2.2.1 - 2.3   |
 |  1.2.0 - 1.3.1   | 2.2.1 - 2.9   |
 | 1.5.0            | 2.2.1 - 2.13  |
 | 2.0.0 - 2.1.2    | 2.10 - 2.13   |
 | 2.1.3 - 2.2.3    | 2.14.1+       |
 | 2.3.0+           | 3.3++         |
 | 3.0.0+           | 4.1+          |
 | 3.1.0+           | 4.4+          |
 | 3.2.0 - 3.2.1    | 4.6+          |
 | 3.3.0            | 4.10.1+       |
 
 表格来源：https://developer.android.com/studio/releases/gradle-plugin.html#updating-gradle
 
	
	
