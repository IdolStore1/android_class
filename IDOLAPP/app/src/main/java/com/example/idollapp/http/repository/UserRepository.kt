package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.ApiWrapper
import com.example.idollapp.http.bean.LoginResponse
import com.example.idollapp.http.bean.UserResponse

class UserRepository {


    private fun createApi(): UserApi {
        return ApiWrapper.create(UserApi::class.java)
    }


    suspend fun loginPasswd(
        phone: String? = null,
        password: String? = null,
    ): Result<ApiResponse<LoginResponse>> {

        val params = HashMap<String, Any>()
        phone?.let { params.put("phone", phone) }
        password?.let { params.put("password", password) }
        return kotlin.runCatching { createApi().loginPasswd(params) }
    }

    suspend fun register(
        username: String? = null,
        phone: String? = null,
        password: String? = null,
    ): Result<ApiResponse<LoginResponse>> {

        val params = HashMap<String, Any>()
        username?.let { params.put("userName", username) }
        phone?.let { params.put("phone", phone) }
        password?.let { params.put("password", password) }

        return kotlin.runCatching { createApi().register(params) }
    }

    suspend fun updateUserInfo(userId: String): Result<ApiResponse<UserResponse>> {
        val params = HashMap<String, Any>()
        return kotlin.runCatching { createApi().updateUserInfo(userId, params) }
    }

}