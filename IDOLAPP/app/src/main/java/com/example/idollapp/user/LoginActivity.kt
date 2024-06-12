package com.example.idollapp.user

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.R
import com.example.idollapp.http.repository.UserRepository
import com.example.idollapp.main.MainActivity
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.ui.view.shotToast
import com.example.idollapp.user.usermanager.AppUserInfoStorage
import com.example.idollapp.utils.BuildParams
import com.example.idollapp.utils.BuildType
import kotlinx.coroutines.launch

class LoginActivity : BaseComposeActivity() {

    @Composable
    override fun RenderContent() {
        Column(modifier = Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold

            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .weight(6f)
                    .background(Color.White, RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp))
            ) {
                Content()
            }
        }

    }

    @Composable
    fun Content(modifier: Modifier = Modifier) {

        val context = LocalContext.current.applicationContext
        // A surface container using the 'background' color from the theme
        val userRepository: IUserRepository = remember {
            when (BuildParams.getBuildType()) {
                BuildType.Dev -> IUserRepositoryDebug(context)
                BuildType.Test -> IUserRepositoryRemote(context, UserRepository())
            }
        }
        val iLoginScreen = remember { mutableStateOf<ILoginScreen>(ILoginScreen.Login) }
        val coroutineScope = rememberCoroutineScope()
        val userStorage = remember { AppUserInfoStorage(context.applicationContext) }
        val login = userStorage.checkUserInfoLogin().collectAsState(initial = null)
        when (login.value) {
            null -> {
                CircularProgressIndicator()
            }

            true -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

            false -> {
                when (iLoginScreen.value) {
                    ILoginScreen.Login ->
                        LoginScreen(
                            { iLoginScreen.value = ILoginScreen.Register },
                            { phone, password ->
                                coroutineScope.launch {
                                    // 登录逻辑
                                    userRepository.login(phone, password).collect {
                                        when (it) {
                                            is LoadingData.Error -> {
                                                shotToast(context, "登录失败 ")
                                            }

                                            is LoadingData.Loading -> {
                                                shotToast(context, "登录中")
                                            }

                                            is LoadingData.Success -> {
                                                startActivity(
                                                    Intent(context, MainActivity::class.java)
                                                )
                                                finish()
                                            }
                                        }
                                    }
                                }

                            })

                    ILoginScreen.Register -> {
                        RegisterScreen({ username, phone, password ->
                            coroutineScope.launch {
                                // 注册逻辑
                                userRepository.register(username, phone, password).collect {
                                    when (it) {
                                        is LoadingData.Error -> {
                                            shotToast(context, "注册失败 ")
                                        }

                                        is LoadingData.Loading -> {
                                            // shotToast(context, "登录中")
                                        }

                                        is LoadingData.Success -> {
                                            shotToast(context, "注册成功")
                                            iLoginScreen.value = ILoginScreen.Login
                                        }
                                    }
                                }
                            }
                        }, {
                            iLoginScreen.value = ILoginScreen.Login
                        })
                    }
                }
            }

        }

    }
}
