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

```shell
# cd UltronxrAIOServiceCenter
mvn clean install
mvn clean install -pl UAIOSC-common
mvn clean install -pl UAIOSC-framework
mvn clean install -pl UAIOSC-system
mvn clean install -pl UAIOSC-game-register
mvn clean package -pl UAIOSC-web
```
