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

使用多模块版本统一控制（在 [根项目pom.xml](./pom.xml#L38) 中统一配置版本号），需要引入 [Maven Flatten Plugin 插件](./pom.xml#L351) 。

```shell
cd UltronxrAIOServiceCenter
mvn clean install package
# mvn clean install -pl <module>
```

## 敏感信息配置文件（未包含在 commit 内，按需手动添加）

### /UAIOSC-web/src/main/resources/config/`aliCloudConfig.properties`

```properties
# 阿里云天气接口appKey
ali.weatherAPI.app.key=
ali.weatherAPI.app.secret=
ali.weatherAPI.app.code=

# 阿里云OSS子用户
ali.subUser.accessKey.id=
ali.subUser.accessKey.secret=

# 阿里云OSS配置
ali.oss.endPoint=
ali.oss.bucketName=
ali.oss.folderKey=

```

### /UAIOSC-web/src/main/resources/config/`tencentCloudConfig.properties`

```properties
## 腾讯云配置文件 ##

# 腾讯云账户的SecretId和SecretKey
secret.id=
secret.key=

# 腾讯云短信服务APP信息
app.name=
app.id=
app.key=
app.createTime=

```

### /UAIOSC-web/src/main/resources/config/`sms.properties`

```properties
## 发送短信配置文件 ##

# 短信签名
sign=

# 短信模板ID
template.id.xx=

# 短消息接收手机号，使用空格分隔
phoneNumber.electricityBill=

```

### /UAIOSC-web/src/main/resources/config/`wechat.properties`

```properties
# 微信公众平台测试号 https://mp.weixin.qq.com/debug/cgi-bin/sandbox?t=sandbox/login

## 测试号appID、appSecret
sandbox.app.id=
sandbox.app.secret=

## 测试号消息接口配置
sandbox.messageInterface.url=
sandbox.messageInterface.token=

```
