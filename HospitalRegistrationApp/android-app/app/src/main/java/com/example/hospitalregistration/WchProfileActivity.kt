package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalregistration.util.WhmSessionManager

class WchProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val session = WhmSessionManager(this)
        findViewById<TextView>(R.id.txtInfo).text =
            "个人中心\n\n姓名：" + session.name() + "\n手机号：" + session.phone()

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            session.clear()
            val intent = Intent(this, WchLoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
