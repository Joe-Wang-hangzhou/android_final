package com.example.hospitalregistration

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.WhmScheduleAdapter
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.Appointment
import com.example.hospitalregistration.model.AppointmentRequest
import com.example.hospitalregistration.model.Doctor
import com.example.hospitalregistration.model.Schedule
import com.example.hospitalregistration.network.WhmRetrofitClient
import com.example.hospitalregistration.util.WhmSessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhmDoctorDetailActivity : AppCompatActivity() {
    private var doctorId: Long = 0
    private var selectedSchedule: Schedule? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail)

        doctorId = intent.getLongExtra("doctorId", 0)
        if (doctorId == 0L) {
            Toast.makeText(this, "医生信息无效", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        loadDoctor()
        loadSchedules()

        findViewById<Button>(R.id.btnSubmitAppointment).setOnClickListener {
            submitAppointment()
        }
    }

    private fun loadDoctor() {
        WhmRetrofitClient.api.doctor(doctorId).enqueue(object : Callback<Doctor> {
            override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                val doctor = response.body()
                if (doctor == null) {
                    Toast.makeText(this@WhmDoctorDetailActivity, "医生信息加载失败", Toast.LENGTH_SHORT).show()
                    return
                }
                findViewById<TextView>(R.id.txtDoctor).text =
                    "医生：" + doctor.name + "\n" +
                    "职称：" + (doctor.title ?: "") + "\n" +
                    "擅长：" + (doctor.specialty ?: "") + "\n" +
                    "简介：" + (doctor.introduction ?: "")
            }

            override fun onFailure(call: Call<Doctor>, t: Throwable) {
                Toast.makeText(this@WhmDoctorDetailActivity, "医生信息加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadSchedules() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        WhmRetrofitClient.api.schedules(doctorId).enqueue(object : Callback<List<Schedule>> {
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = WhmScheduleAdapter(list) { schedule ->
                    selectedSchedule = schedule
                    findViewById<TextView>(R.id.txtSelectedSchedule).text =
                        "已选择：" + schedule.workDate + " " + schedule.timePeriod +
                        "（剩余 " + schedule.leftNumber + " 号）"
                }
            }

            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Toast.makeText(this@WhmDoctorDetailActivity, "排班加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun submitAppointment() {
        val schedule = selectedSchedule
        if (schedule == null) {
            Toast.makeText(this, "请先选择排班", Toast.LENGTH_SHORT).show()
            return
        }

        val patientName = findViewById<EditText>(R.id.edtPatientName).text.toString().trim()
        val patientPhone = findViewById<EditText>(R.id.edtPatientPhone).text.toString().trim()
        if (patientName.isEmpty() || patientPhone.isEmpty()) {
            Toast.makeText(this, "请填写就诊人信息", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = WhmSessionManager(this).userId()
        val request = AppointmentRequest(
            userId = userId,
            doctorId = doctorId,
            scheduleId = schedule.id,
            patientName = patientName,
            patientPhone = patientPhone
        )

        WhmRetrofitClient.api.createAppointment(request).enqueue(object : Callback<ApiResponse<Appointment>> {
            override fun onResponse(
                call: Call<ApiResponse<Appointment>>,
                response: Response<ApiResponse<Appointment>>
            ) {
                val body = response.body()
                if (body != null && body.success) {
                    Toast.makeText(this@WhmDoctorDetailActivity, body.message ?: "预约成功", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@WhmDoctorDetailActivity, body?.message ?: "预约失败", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse<Appointment>>, t: Throwable) {
                Toast.makeText(this@WhmDoctorDetailActivity, "提交预约失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
