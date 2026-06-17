package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.WchNoticeAdapter
import com.example.hospitalregistration.model.Notice
import com.example.hospitalregistration.network.WhmRetrofitClient
import com.example.hospitalregistration.util.WhmSessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WchHomeActivity : AppCompatActivity() {
    private val hospitalList = arrayOf("市人民医院", "中心医院", "妇幼保健院")
    private var selectedHospital = "市人民医院"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val session = WhmSessionManager(this)
        findViewById<TextView>(R.id.txtWelcome).text = "欢迎，" + session.name()
        initHospitalSpinner()
        findViewById<Button>(R.id.btnDepartment).setOnClickListener {
            val intent = Intent(this, WhmDepartmentActivity::class.java)
            intent.putExtra("hospitalName", selectedHospital)
            startActivity(intent)
        }
        findViewById<Button>(R.id.btnAppointments).setOnClickListener {
            startActivity(Intent(this, WchMyAppointmentsActivity::class.java))
        }
        findViewById<Button>(R.id.btnProfile).setOnClickListener {
            startActivity(Intent(this, WchProfileActivity::class.java))
        }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        WhmRetrofitClient.api.notices().enqueue(object : Callback<List<Notice>> {
            override fun onResponse(call: Call<List<Notice>>, response: Response<List<Notice>>) {
                recyclerView.adapter = WchNoticeAdapter(response.body() ?: ArrayList())
            }

            override fun onFailure(call: Call<List<Notice>>, t: Throwable) {
                Toast.makeText(this@WchHomeActivity, "公告加载失败", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initHospitalSpinner() {
        val prefs = getSharedPreferences("wch_hospital", MODE_PRIVATE)
        selectedHospital = prefs.getString("hospitalName", hospitalList[0]) ?: hospitalList[0]
        val spinner = findViewById<Spinner>(R.id.spinnerHospital)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hospitalList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        var index = 0
        for (i in hospitalList.indices) {
            if (hospitalList[i] == selectedHospital) {
                index = i
            }
        }
        spinner.setSelection(index)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                selectedHospital = hospitalList[position]
                prefs.edit().putString("hospitalName", selectedHospital).apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
}
