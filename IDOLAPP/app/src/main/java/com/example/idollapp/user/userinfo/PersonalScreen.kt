package com.example.idollapp.user.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.R
import com.example.idollapp.address.ManagerAddressActivity
import com.example.idollapp.collection.CollectionListActivity
import com.example.idollapp.order.OrderListActivity
import com.example.idollapp.user.usermanager.AppUserManager

@Preview
@Composable
private fun PreviewPersonal() {
    PersonalFragment(
        userName = "ahahah",
        userSignature = "oooooo",
        onFollowClick = { },
        onFavoritesClick = { },
        onOrdersClick = { },
        onAddressClick = { }) {

    }
}

@Composable
fun PersonalScreen(modifier: Modifier = Modifier) {
    val user = AppUserManager.instance().subUserInfoState().collectAsState(initial = null).value

    val context = LocalContext.current
    when (user) {
        is com.example.idollapp.user.usermanager.UserState.Logged -> {
            PersonalFragment(
                userName = user.userInfo.nickname ?: "",
                userSignature = user.userInfo.signature ?: "这个人很懒，什么都没写",
                onFollowClick = { },
                onFavoritesClick = {
                    context.startActivity(CollectionListActivity.start(context))
                },
                onOrdersClick = {
                    context.startActivity(OrderListActivity.start(context))
                },
                onAddressClick = {
                    context.startActivity(ManagerAddressActivity.start(context))
                },
                onEditClick = {
                    context.startActivity(UserInfoActivity.start(context))
                })
        }

        is com.example.idollapp.user.usermanager.UserState.Logout -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "未登录")
            }
        }

        null -> {
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun PersonalFragment(
    userName: String,
    userSignature: String,
    onFollowClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onAddressClick: () -> Unit,
    onEditClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F8F9))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_avatar),
                    contentDescription = null,
                    modifier = Modifier,
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = userName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = userSignature, fontSize = 16.sp, color = Color.Gray)
                }
                Image(
                    painter = painterResource(id = R.drawable.ic_edit),
                    contentDescription = null,
                    modifier = Modifier
                        .size(33.dp)
                        .clickable { onEditClick() }
                )
            }
        }

//        OptionItem(
//            text = "我的关注",
//            iconResId = R.drawable.ic_guanzhu,
//            onClick = onFollowClick
//        )

        OptionItem(
            text = "我的收藏",
            iconResId = R.drawable.ic_shoucang,
            onClick = onFavoritesClick
        )

        OptionItem(
            text = "我的订单",
            iconResId = R.drawable.ic_dindan,
            onClick = onOrdersClick
        )

        OptionItem(
            text = "地址管理",
            iconResId = R.drawable.ic_dizhi,
            onClick = onAddressClick
        )
    }
}

@Composable
fun OptionItem(
    text: String,
    iconResId: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = null,
                modifier = Modifier.size(44.dp)
            )
            Text(
                text = text,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.right),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

