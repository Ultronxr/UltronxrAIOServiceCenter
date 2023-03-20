# UltronxrAIOServiceCenter

Ultronxr 服务中心，SpringBoot 多模块项目架构。

## 环境

openjdk 11.0.10 2021-01-19 (AdoptOpenJDK11U-LTS-jdk_x64_windows_hotspot_11.0.10_9)

IntelliJ IDEA 2022.2


## 项目构建

项目由 [web 模块](./UAIOSC-web) 启动。

### IDE 内运行

给 [WebApplication](./UAIOSC-web/src/main/java/cn/ultronxr/web/WebApplication.java) 添加启动项即可运行。

### 打包

打包成一个可以直接运行的最终 jar 包。

使用多模块版本统一控制（在 [根项目pom.xml](./pom.xml#L36) 中统一配置版本号），需要引入 [Maven Flatten Plugin 插件](./pom.xml#L394) 。

```shell
cd UltronxrAIOServiceCenter
mvn clean install package
# mvn clean install -pl <module>
```
