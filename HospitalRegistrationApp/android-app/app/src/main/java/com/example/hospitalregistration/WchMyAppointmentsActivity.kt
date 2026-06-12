package com.example.hospitalregistration

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.WchAppointmentAdapter
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.Appointment
import com.example.hospitalregistration.network.WhmRetrofitClient
import com.example.hospitalregistration.util.WhmSessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WchMyAppointmentsActivity : AppCompatActivity() {
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
        val userId = WhmSessionManager(this).userId()
        WhmRetrofitClient.api.appointments(userId).enqueue(object : Callback<List<Appointment>> {
            override fun onResponse(call: Call<List<Appointment>>, response: Response<List<Appointment>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = WchAppointmentAdapter(list) { appointment ->
                    cancelAppointment(appointment.id)
                }
            }

            override fun onFailure(call: Call<List<Appointment>>, t: Throwable) {
                Toast.makeText(this@WchMyAppointmentsActivity, "预约记录加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun cancelAppointment(id: Long) {
        WhmRetrofitClient.api.cancelAppointment(id).enqueue(object : Callback<ApiResponse<Appointment>> {
            override fun onResponse(call: Call<ApiResponse<Appointment>>, response: Response<ApiResponse<Appointment>>) {
                val body = response.body()
                Toast.makeText(this@WchMyAppointmentsActivity, body?.message ?: "已取消", Toast.LENGTH_SHORT).show()
                loadAppointments()
            }

            override fun onFailure(call: Call<ApiResponse<Appointment>>, t: Throwable) {
                Toast.makeText(this@WchMyAppointmentsActivity, "取消失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
