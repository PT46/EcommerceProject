package com.example.ecommerce.remote

import com.example.ecommerce.model.LoginRequest
import com.example.ecommerce.model.LoginResponse
import com.example.ecommerce.model.SignUpRequest
import com.example.ecommerce.model.SignUpResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("User/register")
    fun registerUser(
        @Body signupRequest: SignUpRequest
    ): Call<SignUpResponse>

    @Headers("Content-Type: application/json")
    @POST("User/auth")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<LoginResponse>
}
