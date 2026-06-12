package com.example.hospitalregistration

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hospitalregistration.model.ApiResponse
import com.example.hospitalregistration.model.RegisterRequest
import com.example.hospitalregistration.model.User
import com.example.hospitalregistration.network.WhmRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WchRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val edtName = findViewById<EditText>(R.id.edtName)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val edtPassword = findViewById<EditText>(R.id.edtPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val request = RegisterRequest(
                edtName.text.toString(),
                edtPhone.text.toString(),
                edtPassword.text.toString()
            )
            WhmRetrofitClient.api.register(request).enqueue(object : Callback<ApiResponse<User>> {
                override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                    val body = response.body()
                    if (body != null && body.success) {
                        Toast.makeText(this@WchRegisterActivity, "注册成功，请登录", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@WchRegisterActivity, body?.message ?: "注册失败", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                    Toast.makeText(this@WchRegisterActivity, "无法连接后端服务", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
