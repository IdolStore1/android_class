package com.example.idollapp.http

import kotlinx.coroutines.flow.Flow

interface ILoginStateStorage {
    suspend fun isRemember(): Flow<Boolean>
    suspend fun setRemember(remember: Boolean)
}
