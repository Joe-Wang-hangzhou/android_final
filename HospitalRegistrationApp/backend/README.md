# Spring Boot 后端运行说明

## 准备数据库

```sql
CREATE DATABASE hospital_registration DEFAULT CHARACTER SET utf8mb4;
```

修改 `src/main/resources/application.properties`：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/hospital_registration?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_unicode_ci&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的密码
```

如果数据库中中文已经出现乱码，先删除旧库重建最干净：

```sql
DROP DATABASE hospital_registration;
CREATE DATABASE hospital_registration DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

如果不想删库，可以执行 `database-setup.sql` 中的编码修复语句。

## 启动服务

```powershell
mvn spring-boot:run
```

启动成功后访问：

```text
http://localhost:8080/api/departments
```

## 测试账号

```text
手机号：13800000000
密码：123456
```
