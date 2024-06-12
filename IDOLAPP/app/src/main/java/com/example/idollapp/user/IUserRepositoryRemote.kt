package com.example.idollapp.user

import android.content.Context
import com.example.idollapp.http.AppUserTokenStorage
import com.example.idollapp.http.bean.toAppUserInfo
import com.example.idollapp.http.repository.UserRepository
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.ui.base.emitError
import com.example.idollapp.ui.base.emitLoading
import com.example.idollapp.ui.base.emitSuccess
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class IUserRepositoryRemote(
    private val context: Context,
    private val userRepository: UserRepository
) : IUserRepository {
    override fun login(phone: String, password: String): Flow<LoadingData<Boolean>> {
        return flow {
            emitLoading()
            userRepository.loginPasswd(phone, password)
                .onSuccess {
                    if (it.isSuccess) {
                        it.data?.let {
                            AppUserTokenStorage(context.applicationContext).saveToken(it.token)
                            AppUserManager.instance().updateInfo(it.user.toAppUserInfo().apply {
                                Timber.d(" user info : $this")
                            })
                            emitSuccess(true)
                        } ?: let {
                            emitError("数据错误，data 为空！")
                        }
                    } else {
                        emitError(it.message)
                    }
                }
                .onFailure {
                    it.printStackTrace()
                    it.message?.let { it1 -> emitError(it1) }
                }
        }
    }

    override fun register(
        username: String,
        phone: String,
        password: String
    ): Flow<LoadingData<Boolean>> {
        return flow {
            // 注册逻辑
            userRepository.register(username, phone, password)
                .onSuccess { emitSuccess(it.isSuccess) }
                .onFailure {
                    it.printStackTrace()
                    it.message?.let { it1 -> emitError(it1) }
                }
        }
    }
}