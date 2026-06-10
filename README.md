# 医院网上预约挂号手机客户端

这是一个移动终端软件开发课程项目，主题是“医院网上预约挂号手机客户端”。项目包含 Android 客户端、Spring Boot 后端服务和 MySQL 数据库，实现用户在线查看科室医生、查询排班、提交预约和管理预约记录。

## 技术栈

| 模块 | 技术 |
|---|---|
| Android 客户端 | Kotlin + XML + Retrofit |
| 后端服务 | Java + Spring Boot + Spring Data JPA |
| 数据库 | MySQL |
| 接口通信 | REST API + JSON |

## 项目结构

```text
HospitalRegistrationApp/
├── android-app/      Android 客户端项目
├── backend/          Spring Boot 后端项目
├── docs-api.md       接口说明
├── docs-database.md  数据库说明
└── README.md
```

## 功能模块

1. 用户注册：新用户填写姓名、手机号和密码注册账号。
2. 用户登录：用户通过手机号和密码登录系统。
3. 首页公告：展示医院公告和门诊安排。
4. 科室浏览：查看医院科室列表。
5. 医生浏览：按科室查看医生列表。
6. 医生详情与排班：查看医生信息、擅长方向和可预约排班。
7. 在线预约挂号：填写就诊人信息，选择排班并确认预约。
8. 我的预约：查看预约记录，支持取消预约。

## 数据库表

项目使用 MySQL，数据库名为 `hospital_registration`，包含 6 张主要数据表：

| 表名 | 说明 |
|---|---|
| `users` | 用户信息 |
| `departments` | 科室信息 |
| `doctors` | 医生信息 |
| `schedules` | 医生排班和剩余号源 |
| `appointments` | 预约挂号记录 |
| `notices` | 首页公告 |

## 启动教程

### 1. 准备环境

另一台电脑需要安装：

- JDK 17
- Maven
- MySQL
- Android Studio
- Android SDK 和模拟器

### 2. 创建数据库

启动 MySQL 后，执行：

```sql
CREATE DATABASE hospital_registration DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 修改后端数据库配置

打开：

```text
backend/src/main/resources/application.properties
```

把 MySQL 用户名和密码改成自己电脑上的配置：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_registration?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的MySQL密码
```

如果 MySQL 端口不是 `3306`，需要同步修改 URL 中的端口。

### 4. 启动后端

在项目根目录进入后端目录：

```powershell
cd backend
mvn spring-boot:run
```

启动成功后，浏览器访问：

```text
http://localhost:8080/api/departments
```

如果能看到科室数据，说明后端和数据库连接成功。

### 5. 启动 Android 客户端

用 Android Studio 打开：

```text
android-app
```

等待 Gradle 同步完成后，启动 Android 模拟器并运行 App。

客户端默认请求地址是：

```text
http://10.0.2.2:8080/
```

这是 Android 模拟器访问电脑本机后端服务的地址。

如果使用真机运行，需要把 `android-app/app/src/main/java/com/example/hospitalregistration/network/RetrofitClient.kt` 中的 `BASE_URL` 改成电脑的局域网 IP，例如：

```kotlin
private const val BASE_URL = "http://192.168.1.10:8080/"
```

## 测试账号

后端首次启动后会导入测试数据：

```text
手机号：13800000000
密码：123456
```
