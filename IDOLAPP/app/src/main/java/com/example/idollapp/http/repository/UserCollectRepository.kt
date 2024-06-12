package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.ApiWrapper
import com.example.idollapp.http.bean.CheckFavoritesResponse
import com.example.idollapp.http.bean.EmptyResponse

class UserCollectRepository {

    private fun createApi(): UserCollectApi {
        return ApiWrapper.create(UserCollectApi::class.java)
    }

    suspend fun addToCollect(userId: Int? = null, spuId: Int? = null): Result<ApiResponse<EmptyResponse>> {
        val params = HashMap<String, Any>().apply {
            userId?.let { put("userId", it) }
            spuId?.let { put("spuId", it) }
        }
        return kotlin.runCatching { createApi().addToCollect(params) }
    }

    suspend fun checkFavorites(userId: Int? = null): Result<ApiResponse<CheckFavoritesResponse>> {
        val params = HashMap<String, Any>().apply {
            userId?.let { put("userId", it) }
        }
        return kotlin.runCatching { createApi().checkFavorites(params) }
    }

}
