package com.example.hospitalregistration.util

import android.content.Context

class SessionManager(context: Context) {
    private val sp = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveUser(id: Long, name: String, phone: String) {
        val editor = sp.edit()
        editor.putLong("userId", id)
        editor.putString("name", name)
        editor.putString("phone", phone)
        editor.apply()
    }

    fun userId(): Long {
        return sp.getLong("userId", 0)
    }

    fun name(): String {
        return sp.getString("name", "") ?: ""
    }

    fun phone(): String {
        return sp.getString("phone", "") ?: ""
    }

    fun clear() {
        sp.edit().clear().apply()
    }
}
