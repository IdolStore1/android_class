package com.example.idollapp.user.usermanager

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class AppUserInfo(@SerializedName("id") var id: String) {


    @SerializedName("username")
    var nickname: String? = ""

    @SerializedName("sign")
    var signature: String? = ""

    @SerializedName("gender")
    var gender: Int = 0

    override fun toString(): String {
        return "AppUserInfo{" +
                "username='" + nickname + '\'' +
                ", signature='" + signature + '\'' +
                ", id='" + id + '\'' +
                '}'
    }
}
