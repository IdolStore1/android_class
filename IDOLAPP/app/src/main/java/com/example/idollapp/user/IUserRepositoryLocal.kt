package com.example.idollapp.user

import com.example.idollapp.database.UserRepository
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.ui.base.emitLoading
import com.example.idollapp.ui.base.emitSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IUserRepositoryLocal(private val userRepository: UserRepository) : IUserRepository {
    override fun login(phone: String, password: String): Flow<LoadingData<Boolean>> {
        return flow {
            emitLoading()
            val user = userRepository.login( phone, password)
            emitSuccess(true)
        }
    }

    override fun register(
        username: String,
        phone: String,
        password: String
    ): Flow<LoadingData<Boolean>> {
        return flow {
            emitLoading()
            val register = userRepository.register(username, phone, password)
            if (register > 0) {
                emitSuccess(true)
            } else {
                emitSuccess(false)
            }
        }
    }
}