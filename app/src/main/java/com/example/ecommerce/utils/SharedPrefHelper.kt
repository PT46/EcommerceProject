package com.example.ecommerce.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ecommerce.data.model.User
import com.google.gson.Gson

class SharedPrefHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("ecommerce_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    // 🟢 Save login status
    fun saveLoginStatus(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("logged_in", false)
    }

    // 🟢 Save user data after login
    fun saveUser(user: User) {
        val json = gson.toJson(user)
        prefs.edit().putString("user_data", json).apply()
    }

    // 🟢 Get user data anywhere in app
    fun getUser(): User? {
        val json = prefs.getString("user_data", null)
        return if (json != null) gson.fromJson(json, User::class.java) else null
    }

    // 🟢 Logout / Clear all data
    fun clear() {
        prefs.edit().clear().apply()
    }
}
