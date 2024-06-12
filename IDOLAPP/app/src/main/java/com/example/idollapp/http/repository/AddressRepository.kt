package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.ApiWrapper
import com.example.idollapp.http.bean.AddressListResponse
import com.example.idollapp.http.bean.EmptyResponse
import com.example.idollapp.http.bean.UpdateAddressResponse

class AddressRepository {

    private fun createApi(): AddressApi {
        return ApiWrapper.create(AddressApi::class.java)
    }

    suspend fun setDefaultAddress(
        addressId: Int? = null,
        userId: Int? = null
    ): Result<ApiResponse<Any>> {
        val params = HashMap<String, Any>().apply {
            addressId?.let { put("addressId", it) }
            userId?.let { put("userId", it) }
        }
        return kotlin.runCatching { createApi().setDefaultAddress(params) }
    }

    suspend fun addReceiveAddress(): Result<ApiResponse<EmptyResponse>> {
        val params = HashMap<String, Any>()
        return kotlin.runCatching { createApi().addReceiveAddress(params) }
    }

    suspend fun getAddress(userId: Int): Result<ApiResponse<EmptyResponse>> {
        val params = HashMap<String, Any>()
        return kotlin.runCatching { createApi().getAddress(params) }
    }

    suspend fun updateAddress(id: Int, region: String): Result<ApiResponse<UpdateAddressResponse>> {
        val params = HashMap<String, Any>().apply {
            put("id", id)
            put("region", region)
        }
        return kotlin.runCatching { createApi().updateAddress(params) }
    }

    suspend fun getAddressList(): Result<ApiResponse<AddressListResponse>> {
        return kotlin.runCatching { createApi().getAddressList() }
    }

}
