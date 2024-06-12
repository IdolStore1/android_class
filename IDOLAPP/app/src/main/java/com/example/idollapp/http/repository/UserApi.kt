package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.HttpConstants
import com.example.idollapp.http.bean.LoginResponse
import com.example.idollapp.http.bean.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {

    @POST(HttpConstants.USER_BASE_URL + "user/user/login")
    suspend fun loginPasswd(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<LoginResponse>

    @POST(HttpConstants.USER_BASE_URL + "user/user/register")
    suspend fun register(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<LoginResponse>

    @POST(HttpConstants.USER_BASE_URL + "user/user/updateUserInfo/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: String,
        @Body params: Map<String, @JvmSuppressWildcards Any>
    ): ApiResponse<UserResponse>

}