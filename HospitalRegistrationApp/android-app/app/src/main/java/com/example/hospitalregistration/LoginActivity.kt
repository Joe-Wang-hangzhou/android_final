package com.example.hospitalregistration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.LoginRequest
import com.example.hospitalregistration.model.User
import com.example.hospitalregistration.network.RetrofitClient
import com.example.hospitalregistration.util.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        edtPhone.setText("13800000000")
        edtPassword.setText("123456")

        btnLogin.setOnClickListener {
            val phone = edtPhone.text.toString()
            val password = edtPassword.text.toString()
            RetrofitClient.api.login(LoginRequest(phone, password)).enqueue(object : Callback<ApiResponse<User>> {
                override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                    val body = response.body()
                    if (body != null && body.success && body.data != null) {
                        SessionManager(this@LoginActivity).saveUser(body.data.id, body.data.name, body.data.phone)
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, body?.message ?: "登录失败", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "无法连接后端服务", Toast.LENGTH_SHORT).show()
                }
            })
        }

        btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
