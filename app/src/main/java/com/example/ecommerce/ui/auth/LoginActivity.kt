//package com.example.ecommerce.ui.auth
//
//import android.content.Intent
//import android.os.Bundle
//import android.widget.Toast
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import com.example.ecommerce.databinding.ActivityLoginBinding
//import com.example.ecommerce.data.model.LoginRequest
//import com.example.ecommerce.data.model.LoginResponse
//import com.example.ecommerce.data.remote.ApiClient
//import com.example.ecommerce.data.remote.ApiService
//import com.example.ecommerce.ui.dashboard.DashboardActivity
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class LoginActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityLoginBinding
//    private lateinit var apiService: ApiService
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        binding = ActivityLoginBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        apiService = ApiClient.retrofit.create(ApiService::class.java)
//
//        binding.btnLogin.setOnClickListener { loginUser() }
//        binding.btnSignUp.setOnClickListener {
//            startActivity(Intent(this, SignUpActivity::class.java))
//        }
//    }
//
//    private fun loginUser() {
//        val email = binding.etUserName.text.toString().trim()
//        val password = binding.etUserPassword.text.toString().trim()
//
//        if (email.isEmpty() || password.isEmpty()) {
//            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        val request = LoginRequest(emailId = email, password = password)
//        apiService.loginUser(request).enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful) {
//                    val result = response.body()
//                    if (result?.status == 0) {
//                        Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
//                        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
//                        prefs.edit().putBoolean("isLoggedIn", true).putString("email", email).apply()
//                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
//                        finish()
//                    } else {
//                        Toast.makeText(this@LoginActivity, result?.message ?: "Login failed", Toast.LENGTH_SHORT).show()
//                    }
//                } else {
//                    Toast.makeText(this@LoginActivity, "Server error: ${response.code()}", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                Toast.makeText(this@LoginActivity, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//}


package com.example.ecommerce.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ecommerce.databinding.ActivityLoginBinding
import com.example.ecommerce.ui.dashboard.DashboardActivity
import com.example.ecommerce.utils.SharedPrefHelper
import com.example.ecommerce.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle button click
        binding.btnLogin.setOnClickListener {
            val email = binding.etUserName.text.toString().trim()
            val password = binding.etUserPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.login(email, password)
            }
        }

        // Observe login result
        lifecycleScope.launchWhenStarted {
            viewModel.loginResult.observe(this@LoginActivity) { result ->
                result.onSuccess { response ->
                    if (response.status == 0) {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()

                        // Save login session
                        val prefs = SharedPrefHelper(this@LoginActivity)
                        prefs.saveLoginStatus(true)

                        // Navigate to Dashboard
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, response.message, Toast.LENGTH_SHORT).show()
                    }
                }

                result.onFailure {
                    Toast.makeText(this@LoginActivity, it.localizedMessage ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
