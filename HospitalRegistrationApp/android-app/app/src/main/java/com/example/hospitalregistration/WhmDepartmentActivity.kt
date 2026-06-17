package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.WchDepartmentAdapter
import com.example.hospitalregistration.model.Department
import com.example.hospitalregistration.network.WhmRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhmDepartmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        val hospitalName = intent.getStringExtra("hospitalName") ?: "市人民医院"
        findViewById<TextView>(R.id.txtPageTitle).text = hospitalName + " - 选择科室"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        WhmRetrofitClient.api.departments(hospitalName).enqueue(object : Callback<List<Department>> {
            override fun onResponse(call: Call<List<Department>>, response: Response<List<Department>>) {
                val list = response.body() ?: ArrayList()
                recyclerView.adapter = WchDepartmentAdapter(list) { department ->
                    val intent = Intent(this@WhmDepartmentActivity, WhmDoctorListActivity::class.java)
                    intent.putExtra("departmentId", department.id)
                    intent.putExtra("departmentName", department.name)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<Department>>, t: Throwable) {
                Toast.makeText(this@WhmDepartmentActivity, "科室加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
