# 答辩演示脚本

## 1. 项目介绍

本项目是医院网上预约挂号手机客户端，包含 Android 客户端、Spring Boot 后端和 MySQL 数据库。系统支持用户登录注册、浏览公告、查看科室医生、查询排班、在线预约和取消预约。

## 2. 技术介绍

- Android 客户端：Kotlin + XML + Retrofit
- 后端服务：Java + Spring Boot + Spring Data JPA
- 数据库：MySQL
- 通信方式：REST API + JSON

## 3. 功能演示

1. 启动 Spring Boot 后端服务。
2. 打开 Android Studio，运行 Android 客户端。
3. 使用测试账号登录。
4. 首页展示医院公告。
5. 点击“预约挂号”，进入科室列表。
6. 选择科室后查看医生列表。
7. 进入医生详情，查看医生介绍和排班。
8. 填写就诊人信息，选择排班，点击“确认预约”并在弹窗中提交。
9. 进入“我的预约”，查看预约记录。
10. 点击“取消预约”，展示预约状态变为“已取消”。

## 4. 数据库展示

展示 MySQL 中的 6 张表：`users`、`departments`、`doctors`、`schedules`、`appointments`、`notices`。

## 5. 总结

项目实现了移动端预约挂号的主要业务流程，满足课程要求中的 5 个以上功能模块、5 张以上数据库表和服务器后台服务。
