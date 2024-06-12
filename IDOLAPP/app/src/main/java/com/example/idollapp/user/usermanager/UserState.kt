package com.example.idollapp.user.usermanager

import com.example.idollapp.utils.JsonParse


sealed interface UserState {
    data class Logged(var userInfo: AppUserInfo) : UserState {
        fun updateUserInfo(user: Any) {
            userInfo = JsonParse.fromJson(JsonParse.toJson(user), AppUserInfo::class.java)
        }
    }

    data class Logout(val login: Boolean = false, val msg: String? = null) : UserState
}
