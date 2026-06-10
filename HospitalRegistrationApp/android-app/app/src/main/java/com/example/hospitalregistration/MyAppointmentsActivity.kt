package com.example.hospitalregistration

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.AppointmentAdapter
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.Appointment
import com.example.hospitalregistration.network.RetrofitClient
import com.example.hospitalregistration.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointmentsActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        findViewById<TextView>(R.id.txtPageTitle).text = "我的预约"
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        loadAppointments()
    }

    private fun loadAppointments() {
        val userId = SessionManager(this).userId()
        RetrofitClient.api.appointments(userId).enqueue(object : Callback<List<Appointment>> {
            override fun onResponse(call: Call<List<Appointment>>, response: Response<List<Appointment>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = AppointmentAdapter(list) { appointment ->
                    cancelAppointment(appointment.id)
                }
            }

            override fun onFailure(call: Call<List<Appointment>>, t: Throwable) {
                Toast.makeText(this@MyAppointmentsActivity, "预约记录加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cancelAppointment(id: Long) {
        RetrofitClient.api.cancelAppointment(id).enqueue(object : Callback<ApiResponse<Appointment>> {
            override fun onResponse(call: Call<ApiResponse<Appointment>>, response: Response<ApiResponse<Appointment>>) {
                val body = response.body()
                Toast.makeText(this@MyAppointmentsActivity, body?.message ?: "已取消", Toast.LENGTH_SHORT).show()
                loadAppointments()
            }

            override fun onFailure(call: Call<ApiResponse<Appointment>>, t: Throwable) {
                Toast.makeText(this@MyAppointmentsActivity, "取消失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
