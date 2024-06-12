package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.HttpConstants
import com.example.idollapp.http.bean.AddressListResponse
import com.example.idollapp.http.bean.EmptyResponse
import com.example.idollapp.http.bean.UpdateAddressResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface AddressApi {

    @GET(HttpConstants.USER_BASE_URL + "umsUserReceiveAddress/setDefaultAddress")
    suspend fun setDefaultAddress(@QueryMap params: HashMap<String, Any>): ApiResponse<Any>

    @POST(HttpConstants.USER_BASE_URL + "umsUserReceiveAddress/addReceiveAddress")
    suspend fun addReceiveAddress(@Body params: HashMap<String, @JvmSuppressWildcards Any>): ApiResponse<EmptyResponse>

    @GET(HttpConstants.USER_BASE_URL + "umsUserReceiveAddress/getAddress/1")
    suspend fun getAddress(@QueryMap params: HashMap<String, Any>): ApiResponse<EmptyResponse>

    @POST(HttpConstants.USER_BASE_URL + "umsUserReceiveAddress/updateAddress")
    suspend fun updateAddress(@Body params: HashMap<String, @JvmSuppressWildcards Any>): ApiResponse<UpdateAddressResponse>

    @GET(HttpConstants.USER_BASE_URL + "umsUserReceiveAddress/getAddressList")
    suspend fun getAddressList(): ApiResponse<AddressListResponse>

}
