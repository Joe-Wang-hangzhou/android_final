package com.example.hospitalregistration

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.ScheduleAdapter
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.Appointment
import com.example.hospitalregistration.model.AppointmentRequest
import com.example.hospitalregistration.model.Doctor
import com.example.hospitalregistration.model.Schedule
import com.example.hospitalregistration.network.RetrofitClient
import com.example.hospitalregistration.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorDetailActivity : AppCompatActivity() {
    private var doctorId: Long = 0
    private var selectedSchedule: Schedule? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_detail)
        doctorId = intent.getLongExtra("doctorId", 0)

        findViewById<Button>(R.id.btnSubmitAppointment).setOnClickListener {
            val schedule = selectedSchedule
            if (schedule == null) {
                Toast.makeText(this, "请先选择排班", Toast.LENGTH_SHORT).show()
            } else {
                confirmAppointment(schedule)
            }
        }

        loadDoctor()
        loadSchedules()
    }

    private fun loadDoctor() {
        val txtDoctor = findViewById<TextView>(R.id.txtDoctor)
        RetrofitClient.api.doctor(doctorId).enqueue(object : Callback<Doctor> {
            override fun onResponse(call: Call<Doctor>, response: Response<Doctor>) {
                val doctor = response.body()
                if (doctor != null) {
                    txtDoctor.text = doctor.name + "  " + (doctor.title ?: "") +
                        "\n科室：" + (doctor.department?.name ?: "") +
                        "\n擅长：" + (doctor.specialty ?: "") +
                        "\n简介：" + (doctor.introduction ?: "")
                }
            }

            override fun onFailure(call: Call<Doctor>, t: Throwable) {
                Toast.makeText(this@DoctorDetailActivity, "医生详情加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadSchedules() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        RetrofitClient.api.schedules(doctorId).enqueue(object : Callback<List<Schedule>> {
            override fun onResponse(call: Call<List<Schedule>>, response: Response<List<Schedule>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = ScheduleAdapter(list) { schedule ->
                    selectedSchedule = schedule
                    findViewById<TextView>(R.id.txtSelectedSchedule).text =
                        "已选择：" + schedule.workDate + " " + schedule.timePeriod +
                            "，剩余号源：" + schedule.leftNumber
                }
            }

            override fun onFailure(call: Call<List<Schedule>>, t: Throwable) {
                Toast.makeText(this@DoctorDetailActivity, "排班加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun confirmAppointment(schedule: Schedule) {
        val name = findViewById<EditText>(R.id.edtPatientName).text.toString()
        val phone = findViewById<EditText>(R.id.edtPatientPhone).text.toString()
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "请填写就诊人信息", Toast.LENGTH_SHORT).show()
            return
        }
        AlertDialog.Builder(this)
            .setTitle("确认预约")
            .setMessage("就诊人：" + name + "\n手机号：" + phone + "\n时间：" + schedule.workDate + " " + schedule.timePeriod)
            .setNegativeButton("取消", null)
            .setPositiveButton("提交预约") { _, _ ->
                createAppointment(schedule)
            }
            .show()
    }

    private fun createAppointment(schedule: Schedule) {
        val name = findViewById<EditText>(R.id.edtPatientName).text.toString()
        val phone = findViewById<EditText>(R.id.edtPatientPhone).text.toString()
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "请填写就诊人信息", Toast.LENGTH_SHORT).show()
            return
        }
        val session = SessionManager(this)
        val request = AppointmentRequest(session.userId(), doctorId, schedule.id, name, phone)
        RetrofitClient.api.createAppointment(request).enqueue(object : Callback<ApiResponse<Appointment>> {
            override fun onResponse(call: Call<ApiResponse<Appointment>>, response: Response<ApiResponse<Appointment>>) {
                val body = response.body()
                Toast.makeText(this@DoctorDetailActivity, body?.message ?: "预约完成", Toast.LENGTH_SHORT).show()
                selectedSchedule = null
                findViewById<TextView>(R.id.txtSelectedSchedule).text = "尚未选择排班"
                loadSchedules()
            }

            override fun onFailure(call: Call<ApiResponse<Appointment>>, t: Throwable) {
                Toast.makeText(this@DoctorDetailActivity, "预约失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
