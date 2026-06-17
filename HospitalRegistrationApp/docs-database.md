# 数据库表设计说明

数据库名称：`hospital_registration`

建库语句：

```sql
CREATE DATABASE hospital_registration DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## users 用户表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| name | varchar | 用户姓名 |
| phone | varchar | 手机号，唯一 |
| password | varchar | 登录密码 |

## departments 科室表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| name | varchar | 科室名称 |
| description | varchar | 科室简介 |
| hospital_name | varchar | 所属医院名称 |

## doctors 医生表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| name | varchar | 医生姓名 |
| title | varchar | 职称 |
| specialty | varchar | 擅长方向 |
| introduction | varchar | 医生简介 |
| department_id | bigint | 所属科室 |

## schedules 排班表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| doctor_id | bigint | 医生编号 |
| work_date | date | 出诊日期 |
| time_period | varchar | 上午/下午 |
| total_number | int | 总号源 |
| left_number | int | 剩余号源 |

## appointments 预约表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| user_id | bigint | 用户编号 |
| doctor_id | bigint | 医生编号 |
| schedule_id | bigint | 排班编号 |
| patient_name | varchar | 就诊人姓名 |
| patient_phone | varchar | 就诊人手机号 |
| status | varchar | 已预约/已取消 |
| create_time | datetime | 创建时间 |

## notices 公告表

| 字段 | 类型 | 说明 |
|---|---|---|
| id | bigint | 主键 |
| title | varchar | 公告标题 |
| content | varchar | 公告内容 |
| publish_time | datetime | 发布时间 |
