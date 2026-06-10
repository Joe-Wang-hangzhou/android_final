package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.NoticeAdapter
import com.example.hospitalregistration.model.Notice
import com.example.hospitalregistration.network.RetrofitClient
import com.example.hospitalregistration.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val session = SessionManager(this)
        findViewById<TextView>(R.id.txtWelcome).text = "欢迎，" + session.name()
        findViewById<Button>(R.id.btnDepartment).setOnClickListener {
            startActivity(Intent(this, DepartmentActivity::class.java))
        }
        findViewById<Button>(R.id.btnAppointments).setOnClickListener {
            startActivity(Intent(this, MyAppointmentsActivity::class.java))
        }
        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        RetrofitClient.api.notices().enqueue(object : Callback<List<Notice>> {
            override fun onResponse(call: Call<List<Notice>>, response: Response<List<Notice>>) {
                recyclerView.adapter = NoticeAdapter(response.body() ?: ArrayList())
            }

            override fun onFailure(call: Call<List<Notice>>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "公告加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
