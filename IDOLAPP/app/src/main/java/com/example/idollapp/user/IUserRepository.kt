package com.example.idollapp.user

import com.example.idollapp.ui.base.LoadingData
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun login(phone: String, password: String): Flow<LoadingData<Boolean>>

    fun register(username: String, phone: String, password: String): Flow<LoadingData<Boolean>>

}