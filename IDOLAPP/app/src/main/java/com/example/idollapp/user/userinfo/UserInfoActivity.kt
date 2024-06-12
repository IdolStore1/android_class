package com.example.idollapp.user.userinfo

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.user.usermanager.AppUserInfo
import com.example.idollapp.user.usermanager.AppUserInfoStorage
import com.example.idollapp.user.usermanager.AppUserManager
import com.example.idollapp.user.usermanager.IUserInfoStorage
import com.example.idollapp.user.usermanager.UserState
import kotlinx.coroutines.launch

class UserInfoActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, UserInfoActivity::class.java)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val userState =
            AppUserManager.instance().subUserInfoState().collectAsState(initial = null).value
        val coroutineScope = rememberCoroutineScope()
        when (userState) {
            is UserState.Logged -> {
                userState.userInfo.let {
                    UserInfoScreen(
                        modifier = Modifier.fillMaxSize(),
                        userInfo = userState.userInfo,
                        onInfoChange = {
                            coroutineScope.launch {
//                                val appUserInfoStorage: IUserInfoStorage<AppUserInfo> =
//                                    AppUserInfoStorage(context)
//                                appUserInfoStorage.saveUserInfo(it)
                                AppUserManager.instance().updateInfo(it)
                            }
                        }
                    ) {
                        AppUserManager.instance().logout()
                    }
                }
            }

            is UserState.Logout -> {
                Text(text = "未登录")
            }

            null -> {
                CircularProgressIndicator()
            }
        }

    }

}
