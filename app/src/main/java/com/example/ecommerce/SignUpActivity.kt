package com.example.ecommerce

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecommerce.databinding.ActivitySignUpBinding
import com.example.ecommerce.model.SignUpRequest
import com.example.ecommerce.model.SignUpResponse
import com.example.ecommerce.remote.ApiClient
import com.example.ecommerce.remote.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        apiService = ApiClient.retrofit.create(ApiService::class.java)
        binding.btnRegisterSignup.setOnClickListener {
            performSignUp()
        }
    }

    private fun performSignUp() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmailID.text.toString().trim()
        val mobile = binding.etMobileNumber.text.toString().trim()
        val password = binding.etUserPasswordSignup.text.toString().trim()
        if (fullName.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            return
        }
        if (password.length < 8) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return
        }

        val request = SignUpRequest(
            emailId = email,
            fullName = fullName,
            mobileNo = mobile,
            password = password
        )


        registerUser(request)
    }

    private fun registerUser(request: SignUpRequest) {
        apiService.registerUser(request).enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result?.status == 0) {
                        Toast.makeText(this@SignUpActivity, result.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@SignUpActivity, result?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@SignUpActivity, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
