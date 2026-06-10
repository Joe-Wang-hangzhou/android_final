# 后端接口说明

后端基础地址：

```text
http://localhost:8080/
```

Android 模拟器访问本机后端地址：

```text
http://10.0.2.2:8080/
```

## 用户注册

`POST /api/auth/register`

```json
{
  "name": "测试用户",
  "phone": "13800000000",
  "password": "123456"
}
```

## 用户登录

`POST /api/auth/login`

```json
{
  "phone": "13800000000",
  "password": "123456"
}
```

## 公告列表

`GET /api/notices`

## 科室列表

`GET /api/departments`

## 医生列表

`GET /api/doctors?departmentId=1`

## 医生详情

`GET /api/doctors/1`

## 排班列表

`GET /api/schedules?doctorId=1`

## 创建预约

`POST /api/appointments`

```json
{
  "userId": 1,
  "doctorId": 1,
  "scheduleId": 1,
  "patientName": "测试用户",
  "patientPhone": "13800000000"
}
```

## 我的预约

`GET /api/appointments/user/1`

## 取消预约

`DELETE /api/appointments/1`
