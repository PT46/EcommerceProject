package com.example.ecommerce.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.data.model.LoginResponse
import com.example.ecommerce.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository = AuthRepository()
    val loginResult = MutableLiveData<Result<LoginResponse>>()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val request = LoginRequest(email, password)
            val result = repository.loginUser(request)
            loginResult.postValue(result)
        }
    }
}
