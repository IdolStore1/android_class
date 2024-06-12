package com.example.idollapp.address

import com.example.idollapp.database.UserAddressDao
import com.example.idollapp.database.UserAddressEntity
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AddressRepositoryLocal(
    private val repository: UserAddressDao,
    private val userId: String
) : IAddressRepository {

    override fun addAddress(name: String, phone: String, address: String): Flow<Boolean> {
        return flow {
            val id = repository.insert(
                UserAddressEntity(0, userId, name, phone, address)
            )
            emit(id > 0)
        }
    }

    override fun findAddress(id: Int): Flow<UserAddress> {
        return flow {
            val address = repository.findAddress(id)
            emit(UserAddress(address.id, address.name, address.phone, address.address))
        }
    }

    override fun editAddress(id: Int, name: String, phone: String, address: String): Flow<Boolean> {
        return flow {
            val editId = repository.insert(
                UserAddressEntity(id, userId, name, phone, address)
            )
            emit(editId > 0)
        }
    }

    override fun deleteAddress(id: Int): Flow<Boolean> {
        return flow {
            repository.delete(id)
            emit(true)
        }
    }

    override fun getAddressList(): Flow<List<UserAddress>> {
        val userInfo = AppUserManager.instance().getUserInfo()
        val userId = userInfo?.id ?: ""
        return repository.findAddressListByUserId(userId).map {
            it.map {
                UserAddress(it.id, it.name, it.phone, it.address)
            }
        }
    }
}