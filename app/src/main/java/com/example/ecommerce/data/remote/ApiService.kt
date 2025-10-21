package com.example.ecommerce.data.remote

import com.example.ecommerce.data.model.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // 🟢 1. User Registration
    @POST("User/register")
    fun registerUser(@Body request: SignUpRequest): Call<SignUpResponse>

    // 🟢 2. User Login
    @POST("User/auth")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    // 🟢 3. Get all Categories
    @GET("Category")
    fun getCategories(): Call<CategoryResponse>



}
