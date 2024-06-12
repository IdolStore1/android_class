package com.example.idollapp.http

interface ITokenStorage {

    fun saveToken(token: String)

    fun getToken():String?

    fun cleanToken():Boolean

}