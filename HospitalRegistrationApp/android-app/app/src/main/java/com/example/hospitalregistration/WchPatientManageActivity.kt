package com.example.hospitalregistration

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hospitalregistration.adapter.WchPatientAdapter
import com.example.hospitalregistration.util.WchPatientInfo
import com.example.hospitalregistration.util.WchPatientStorage

class WchPatientManageActivity : AppCompatActivity() {
    private val allList = ArrayList<WchPatientInfo>()
    private val showList = ArrayList<WchPatientInfo>()
    private var selectedPatient: WchPatientInfo? = null
    private lateinit var storage: WchPatientStorage
    private lateinit var adapter: WchPatientAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_manage)

        storage = WchPatientStorage(this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewPatients)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = WchPatientAdapter(showList, { patient ->
            selectPatient(patient)
        }, { patient ->
            deletePatient(patient)
        })
        recyclerView.adapter = adapter

        findViewById<Button>(R.id.btnSearchPatient).setOnClickListener {
            refreshList()
        }
        findViewById<Button>(R.id.btnAddPatient).setOnClickListener {
            addPatient()
        }
        findViewById<Button>(R.id.btnUpdatePatient).setOnClickListener {
            updatePatient()
        }
        findViewById<Button>(R.id.btnClearPatient).setOnClickListener {
            clearInput()
        }

        refreshList()
    }

    private fun refreshList() {
        allList.clear()
        allList.addAll(storage.loadPatients())
        showList.clear()

        val keyword = findViewById<EditText>(R.id.edtSearchPatient).text.toString().trim()
        for (item in allList) {
            if (keyword.isEmpty() || item.name.contains(keyword) || item.phone.contains(keyword)) {
                showList.add(item)
            }
        }
        findViewById<TextView>(R.id.txtPatientCount).text =
            "共 " + allList.size + " 个就诊人，当前显示 " + showList.size + " 个"
        adapter.notifyDataSetChanged()
    }

    private fun addPatient() {
        val name = findViewById<EditText>(R.id.edtPatientName).text.toString().trim()
        val phone = findViewById<EditText>(R.id.edtPatientPhone).text.toString().trim()
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "请填写姓名和手机号", Toast.LENGTH_SHORT).show()
            return
        }

        val added = storage.addPatient(name, phone)
        if (added) {
            Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show()
            clearInput()
            refreshList()
        } else {
            Toast.makeText(this, "该就诊人已存在", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectPatient(patient: WchPatientInfo) {
        selectedPatient = patient
        findViewById<EditText>(R.id.edtPatientName).setText(patient.name)
        findViewById<EditText>(R.id.edtPatientPhone).setText(patient.phone)
    }

    private fun updatePatient() {
        val oldPatient = selectedPatient
        if (oldPatient == null) {
            Toast.makeText(this, "请先选择要修改的就诊人", Toast.LENGTH_SHORT).show()
            return
        }

        val name = findViewById<EditText>(R.id.edtPatientName).text.toString().trim()
        val phone = findViewById<EditText>(R.id.edtPatientPhone).text.toString().trim()
        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "请填写姓名和手机号", Toast.LENGTH_SHORT).show()
            return
        }

        val list = storage.loadPatients()
        var index = -1
        for (i in list.indices) {
            if (list[i].name == oldPatient.name && list[i].phone == oldPatient.phone) {
                index = i
            }
        }
        if (index == -1) {
            Toast.makeText(this, "原就诊人不存在", Toast.LENGTH_SHORT).show()
            refreshList()
            return
        }

        for (i in list.indices) {
            if (i != index && list[i].name == name && list[i].phone == phone) {
                Toast.makeText(this, "该就诊人已存在", Toast.LENGTH_SHORT).show()
                return
            }
        }

        list[index] = WchPatientInfo(name, phone)
        storage.savePatients(list)
        selectedPatient = list[index]
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
        refreshList()
    }

    private fun deletePatient(patient: WchPatientInfo) {
        val list = storage.loadPatients()
        var index = -1
        for (i in list.indices) {
            if (list[i].name == patient.name && list[i].phone == patient.phone) {
                index = i
            }
        }
        if (index >= 0) {
            list.removeAt(index)
            storage.savePatients(list)
            if (selectedPatient?.name == patient.name && selectedPatient?.phone == patient.phone) {
                clearInput()
            }
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show()
            refreshList()
        }
    }

    private fun clearInput() {
        selectedPatient = null
        findViewById<EditText>(R.id.edtPatientName).setText("")
        findViewById<EditText>(R.id.edtPatientPhone).setText("")
    }
}
