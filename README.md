# UltronxrAIOServiceCenter

Ultronxr 服务中心，SpringBoot 多模块项目架构。

## 项目构建

项目由 [web 模块](./web) 启动。

### IDE 内运行

给 [WebApplication](./web/src/main/java/cn/ultronxr/web/WebApplication.java) 添加启动项即可运行。

### 打包

打包成一个可以直接运行的最终 jar 包。

```shell
# cd UltronxrAIOServiceCenter
mvn clean install
mvn clean install -pl common
mvn clean install -pl game-register
mvn clean package -pl web
```
