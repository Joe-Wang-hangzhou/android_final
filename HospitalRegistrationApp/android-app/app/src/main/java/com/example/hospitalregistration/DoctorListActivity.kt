package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.DoctorAdapter
import com.example.hospitalregistration.model.Doctor
import com.example.hospitalregistration.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DoctorListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val departmentId = intent.getLongExtra("departmentId", 0)
        val departmentName = intent.getStringExtra("departmentName") ?: "医生列表"
        findViewById<TextView>(R.id.txtPageTitle).text = departmentName

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        RetrofitClient.api.doctors(departmentId).enqueue(object : Callback<List<Doctor>> {
            override fun onResponse(call: Call<List<Doctor>>, response: Response<List<Doctor>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = DoctorAdapter(list) { doctor ->
                    val intent = Intent(this@DoctorListActivity, DoctorDetailActivity::class.java)
                    intent.putExtra("doctorId", doctor.id)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<Doctor>>, t: Throwable) {
                Toast.makeText(this@DoctorListActivity, "医生加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
