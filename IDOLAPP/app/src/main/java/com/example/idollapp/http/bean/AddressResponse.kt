package com.example.idollapp.http.bean


data class UpdateAddressResponse(
    val msg: String,
    val code: Int
)

data class AddressListResponse(
    val addressInfo: AddressInfo,
    val code: Int
)

data class AddressInfo(
    val name: String,
    val phone: String,
    val postCode: String,
    val province: String,
    val city: String,
    val region: String,
    val detailAddress: String
)