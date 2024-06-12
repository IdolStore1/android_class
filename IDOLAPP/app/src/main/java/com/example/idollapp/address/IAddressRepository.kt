package com.example.idollapp.address

import com.example.idollapp.goods.UserAddress
import kotlinx.coroutines.flow.Flow

interface IAddressRepository {

    fun addAddress(name: String, phone: String, address: String): Flow<Boolean>
    fun findAddress(id: Int): Flow<UserAddress>
    fun editAddress(id: Int, name: String, phone: String, address: String): Flow<Boolean>
    fun deleteAddress(id: Int): Flow<Boolean>
    fun getAddressList(): Flow<List<UserAddress>>

}