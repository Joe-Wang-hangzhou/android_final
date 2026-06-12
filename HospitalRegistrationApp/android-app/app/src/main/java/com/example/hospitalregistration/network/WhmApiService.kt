package com.example.hospitalregistration.network

import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.Appointment
import com.example.hospitalregistration.model.AppointmentRequest
import com.example.hospitalregistration.model.Department
import com.example.hospitalregistration.model.Doctor
import com.example.hospitalregistration.model.LoginRequest
import com.example.hospitalregistration.model.Notice
import com.example.hospitalregistration.model.RegisterRequest
import com.example.hospitalregistration.model.Schedule
import com.example.hospitalregistration.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WhmApiService {
    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<ApiResponse<User>>

    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<ApiResponse<User>>

    @GET("api/notices")
    fun notices(): Call<List<Notice>>

    @GET("api/departments")
    fun departments(): Call<List<Department>>

    @GET("api/doctors")
    fun doctors(@Query("departmentId") departmentId: Long): Call<List<Doctor>>

    @GET("api/doctors/{id}")
    fun doctor(@Path("id") id: Long): Call<Doctor>

    @GET("api/schedules")
    fun schedules(@Query("doctorId") doctorId: Long): Call<List<Schedule>>

    @POST("api/appointments")
    fun createAppointment(@Body request: AppointmentRequest): Call<ApiResponse<Appointment>>

    @GET("api/appointments/user/{userId}")
    fun appointments(@Path("userId") userId: Long): Call<List<Appointment>>

    @DELETE("api/appointments/{id}")
    fun cancelAppointment(@Path("id") id: Long): Call<ApiResponse<Appointment>>
}
