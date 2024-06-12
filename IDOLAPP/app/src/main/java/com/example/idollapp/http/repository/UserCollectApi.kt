package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.HttpConstants
import com.example.idollapp.http.bean.CheckFavoritesResponse
import com.example.idollapp.http.bean.EmptyResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface UserCollectApi {

    @GET(HttpConstants.USER_BASE_URL + "user/usercollectspu/addToCollect")
    suspend fun addToCollect(@QueryMap params: HashMap<String, Any>): ApiResponse<EmptyResponse>

    @GET(HttpConstants.USER_BASE_URL + "user/usercollectspu/checkFavorites")
    suspend fun checkFavorites(@QueryMap params: HashMap<String, Any>): ApiResponse<CheckFavoritesResponse>

}
