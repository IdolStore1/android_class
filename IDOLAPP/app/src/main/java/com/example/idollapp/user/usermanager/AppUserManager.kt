package com.example.idollapp.user.usermanager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 用户信息内存管理工具
 */
class AppUserManager {

    private val userInfoState: MutableStateFlow<UserState> = MutableStateFlow(UserState.Logout())

    fun isLogin(): Boolean {
        return when (userInfoState.value) {
            is UserState.Logged -> {
                true
            }

            is UserState.Logout -> {
                false
            }
        }
    }

    fun getUserInfo(): AppUserInfo? {
        return if (isLogin()) (userInfoState.value as UserState.Logged).userInfo else null
    }

    fun subUserInfoState(): StateFlow<UserState> {
        return userInfoState
    }

    fun updateInfo(userInfo: AppUserInfo) {
        userInfoState.value = UserState.Logged(userInfo)
    }

    fun logout(msg: String? = null) {
        userInfoState.value = UserState.Logout(msg = msg)
    }

    companion object {

        fun instance(): AppUserManager {
            return Holder.instance
        }

        private object Holder {
            val instance = AppUserManager()
        }

    }

}