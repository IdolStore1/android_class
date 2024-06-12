package com.example.idollapp.http

data class RegisterRequest(val phone: String, val password: String)
data class RegisterResponse(val msg: String, val code: Int)

data class LoginRequest(val phone: String, val password: String)
data class LoginResponse(val msg: String, val code: Int)