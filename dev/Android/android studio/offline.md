###配置离线编译依赖项
如果您想在没有网络连接的情况下编译项目，请按照以下步骤配置 Android Studio，以使用 Android Gradle 插件和 Google Maven 依赖项的离线版本。

如果您尚未下载的话，请从下载页面下载[离线组件](https://developer.android.google.cn/r/studio-offline/downloads)

####下载并解压缩离线组件

下载离线组件后，将其内容解压缩到以下目录中，如果该目录尚不存在，您可能需要创建该目录：

- 在 Windows 上：%USER_HOME%/.android/manual-offline-m2/
- 在 macOS 和 Linux 上：~/.android/manual-offline-m2/

要更新离线组件，请按以下步骤操作：

1. 删除 manual-offline-m2/ 目录中的内容。
2. 重新下载离线组件。
3. 将所下载的 ZIP 文件的内容解压缩到 manual-offline-m2/ 目录中。

####在 Gradle 项目中添加离线组件

要告知 Android 编译系统使用您已下载并解压缩的离线组件，您需要创建一个脚本（如下所述）。请注意，即使在更新离线组件之后，您也只需创建并保存此脚本一次。

1. 使用以下路径和文件名创建一个空文本文件：
    - 在 Windows 上：%USER_HOME%/.gradle/init.d/offline.gradle
    - 在 macOS 和 Linux 上：~/.gradle/init.d/offline.gradle
2. 打开该文本文件并添加以下脚本：
    ```
    def reposDir = new File(System.properties['user.home'], ".android/manual-offline-m2")
        def repos = new ArrayList()
        reposDir.eachDir {repos.add(it) }
        repos.sort()
    
        allprojects {
          buildscript {
            repositories {
              for (repo in repos) {
                maven {
                  name = "injected_offline_${repo.name}"
                  url = repo.toURI().toURL()
                }
              }
            }
          }
          repositories {
            for (repo in repos) {
              maven {
                name = "injected_offline_${repo.name}"
                url = repo.toURI().toURL()
              }
            }
          }
        }
    ```
3. 保存该文本文件。
4. （可选）如果您想要验证离线组件是否运行正常，请从项目的 build.gradle 文件中移除在线代码库（如下所示）。在确认项目不使用这些代码库也能正确编译之后，您可以将它们放回到 build.gradle 文件中。

```groovy
buildscript {
        repositories {
            // Hide these repositories to test your build against
            // the offline components. You can include them again after
            // you've confirmed that your project builds ‘offline’.
            // google()
            // jcenter()
        }
        ...
    }
    allprojects {
        repositories {
            // google()
            // jcenter()
        }
        ...
    }
```