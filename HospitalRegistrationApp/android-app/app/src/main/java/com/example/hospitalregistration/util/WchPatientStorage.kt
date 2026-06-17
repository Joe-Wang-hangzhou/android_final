package com.example.hospitalregistration.util

import android.content.Context
import android.net.Uri

data class WchPatientInfo(val name: String, val phone: String)

class WchPatientStorage(private val context: Context) {
    private val session = WhmSessionManager(context)

    fun loadPatients(): ArrayList<WchPatientInfo> {
        val list = ArrayList<WchPatientInfo>()
        val prefs = context.getSharedPreferences("wch_patients", Context.MODE_PRIVATE)
        var text = prefs.getString(patientKey(), "") ?: ""
        if (text.isEmpty()) {
            val oldText = prefs.getString("patients", "") ?: ""
            if (oldText.isNotEmpty()) {
                prefs.edit().putString(patientKey(), oldText).apply()
                text = oldText
            }
        }
        if (text.isNotEmpty()) {
            val rows = text.split("\n")
            for (row in rows) {
                val parts = row.split("|")
                if (parts.size == 2) {
                    val name = Uri.decode(parts[0])
                    val phone = Uri.decode(parts[1])
                    list.add(WchPatientInfo(name, phone))
                }
            }
        }
        return list
    }

    fun savePatients(list: List<WchPatientInfo>) {
        val rows = ArrayList<String>()
        for (item in list) {
            rows.add(Uri.encode(item.name) + "|" + Uri.encode(item.phone))
        }
        context.getSharedPreferences("wch_patients", Context.MODE_PRIVATE)
            .edit()
            .putString(patientKey(), rows.joinToString("\n"))
            .apply()
    }

    fun addPatient(name: String, phone: String): Boolean {
        val list = loadPatients()
        for (item in list) {
            if (item.name == name && item.phone == phone) {
                return false
            }
        }
        list.add(WchPatientInfo(name, phone))
        savePatients(list)
        return true
    }

    private fun patientKey(): String {
        return "patients_" + session.userId()
    }
}
