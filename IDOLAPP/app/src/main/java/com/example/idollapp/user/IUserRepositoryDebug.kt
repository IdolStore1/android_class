package com.example.idollapp.user

import android.content.Context
import com.example.idollapp.http.AppUserTokenStorage
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.ui.base.emitLoading
import com.example.idollapp.ui.base.emitSuccess
import com.example.idollapp.user.usermanager.AppUserInfo
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class IUserRepositoryDebug(private val context: Context) : IUserRepository {
    override fun login(phone: String, password: String): Flow<LoadingData<Boolean>> {
        return flow {
            emitLoading()
            delay(1000)
            AppUserTokenStorage(context.applicationContext).saveToken("it.tokenit.tokenit.tokenit.tokenit.token")
            AppUserManager.instance().updateInfo(AppUserInfo("hahaha"))
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
            delay(1000)
            emitSuccess(true)
        }
    }
}