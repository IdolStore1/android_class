package com.example.idollapp.http.bean

import com.example.idollapp.user.usermanager.AppUserInfo
import com.example.idollapp.utils.JsonParse


data class UserResponse(
    val id: Long,
    val username: String,
    val nickname: String,
    val phone: String,
    val email: String,
    val avatar: String,
    val gender: Long,
    val lastLoginTime: Long,
    val status: Long,
    val description: String,
    val sign: String,
    val role: Long
)

fun UserResponse.toAppUserInfo(): AppUserInfo {
    return JsonParse.fromJson(JsonParse.toJson(this), AppUserInfo::class.java)
}