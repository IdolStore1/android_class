package com.example.idollapp.user.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idollapp.ui.dialog.GetInputDialog
import com.example.idollapp.ui.dialog.rememberDialogState
import com.example.idollapp.ui.theme.TextColor3D3D3D
import com.example.idollapp.ui.view.rememberBooleanState
import com.example.idollapp.ui.view.rememberStringState
import com.example.idollapp.user.usermanager.AppUserInfo

@Preview
@Composable
private fun PreviewUserInfo() {
    UserInfoScreen(
        modifier = Modifier.fillMaxSize(),
        id = "72634",
        nickname = "userInfo.nickname",
        onNickname = {},
        gender = "女",
        onGender = {},
        signature = "userInfo.signature",
        onSignature = {}
    ) {

    }
}


@Composable
fun UserInfoScreen(
    modifier: Modifier = Modifier,
    userInfo: AppUserInfo,
    onInfoChange: (AppUserInfo) -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    UserInfoScreen(
        modifier = modifier,
        id = userInfo.id,
        nickname = userInfo.nickname ?: "",
        onNickname = {
            userInfo.nickname = it
            onInfoChange(userInfo)
        },
        gender = when (userInfo.gender) {
            0 -> {
                "女"
            }

            1 -> {
                "男"
            }

            else -> {
                "其它"
            }
        },
        onGender = {
            userInfo.gender = it
            onInfoChange(userInfo)
        },
        signature = userInfo.signature ?: "",
        onSignature = {
            userInfo.signature = it
            onInfoChange(userInfo)
        }
    ) { onLogout() }
}

@Composable
private fun UserInfoScreen(
    modifier: Modifier = Modifier,
    id: String,
    nickname: String,
    onNickname: (String) -> Unit,
    gender: String,
    onGender: (Int) -> Unit,
    signature: String,
    onSignature: (String) -> Unit,
    onLogout: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        Column(
            Modifier
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    RoundedCornerShape(bottomEnd = 60.dp, bottomStart = 60.dp)
                )
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.size(20.dp))
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.size(50.dp))
        }

        Spacer(modifier = Modifier.size(20.dp))
        Column(modifier = Modifier.padding(16.dp)) {

            UserInfoItemInputDialog(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                text = "ID",
                value = id,
                onChange = {                }
            )
            Divider()
            val nicknameState = rememberStringState(nickname)
            UserInfoItemInputDialog(
                modifier = Modifier.fillMaxWidth(),
                text = "昵称",
                value = nicknameState.get(),
                onChange = {
                    nicknameState.update(it)
                    onNickname(it)
                }
            )
            Divider()
            UserGenderItem(
                modifier = Modifier.fillMaxWidth(),
                text = "性别",
                value = gender,
                onChange = {
                    onGender(it)
                }
            )
            Divider()
            val signatureState = rememberStringState(signature)
            UserInfoItemInputDialog(
                modifier = Modifier.fillMaxWidth(),
                text = "签名",
                value = signatureState.get(),
                onChange = {
                    signatureState.update(it)
                    onSignature(it)
                }
            )
            Divider()
            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = { onLogout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Red,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(text = "退出账号", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))

        }
    }

}


@Composable
fun UserGenderItem(
    modifier: Modifier = Modifier,
    text: String,
    value: String,
    onChange: (Int) -> Unit
) {
    val genderDialog = rememberBooleanState()
    UserInfoItem(modifier, {
        genderDialog.beTrue()
    }, true, text, value)

    if (genderDialog.get()) {
        GenderSelectionDialog(
            onDismiss = { genderDialog.beFalse() },
            onGenderSelected = { gender ->
                onChange(gender)
            }
        )
    }
}

@Composable
fun UserInfoItemInputDialog(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    value: String,
    onChange: (String) -> Unit
) {
    val dialogState = rememberDialogState()
    UserInfoItem(modifier, {
        dialogState.open()
    }, enabled, text, value)
    GetInputDialog(
        dialogState = dialogState,
        title = text,
        hint = "请输入$text",
        defaultInput = value,
        onDismiss = {
            dialogState.close()
        },
        onConfirm = {
            onChange(it)
            dialogState.close()
        })
}

@Composable
private fun UserInfoItem(
    modifier: Modifier,
    onClick: () -> Unit,
    enabled: Boolean,
    text: String,
    value: String
) {
    TextButton(
        onClick = {
            onClick()
        },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = TextColor3D3D3D)
    ) {
        Text(text = text)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value)
        Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "")
    }
}