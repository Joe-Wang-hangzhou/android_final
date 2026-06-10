# Android 客户端运行说明

## 打开方式

使用 Android Studio 打开 `android-app` 目录，等待 Gradle 同步完成后运行。

## 后端地址

客户端默认访问：

```text
http://10.0.2.2:8080/
```

该地址用于 Android 模拟器访问电脑本机后端服务。如果使用真机测试，需要把 `RetrofitClient.kt` 中的地址改成电脑在局域网中的 IP 地址。

## 测试账号

```text
手机号：13800000000
密码：123456
```
