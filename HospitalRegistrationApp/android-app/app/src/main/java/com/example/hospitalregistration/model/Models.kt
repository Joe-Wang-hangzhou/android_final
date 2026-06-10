package com.example.hospitalregistration.model

data class ApiResponse<T>(
    val success: Boolean,
    val message: String?,
    val data: T?
)

data class LoginRequest(val phone: String, val password: String)
data class RegisterRequest(val name: String, val phone: String, val password: String)
data class AppointmentRequest(
    val userId: Long,
    val doctorId: Long,
    val scheduleId: Long,
    val patientName: String,
    val patientPhone: String
)

data class User(val id: Long, val name: String, val phone: String)
data class Department(val id: Long, val name: String, val description: String?)
data class Doctor(
    val id: Long,
    val name: String,
    val title: String?,
    val specialty: String?,
    val introduction: String?,
    val department: Department?
)
data class Schedule(
    val id: Long,
    val doctor: Doctor?,
    val workDate: String,
    val timePeriod: String,
    val totalNumber: Int,
    val leftNumber: Int
)
data class Notice(val id: Long, val title: String, val content: String, val publishTime: String?)
data class Appointment(
    val id: Long,
    val user: User?,
    val doctor: Doctor?,
    val schedule: Schedule?,
    val patientName: String?,
    val patientPhone: String?,
    val status: String?,
    val createTime: String?
)
