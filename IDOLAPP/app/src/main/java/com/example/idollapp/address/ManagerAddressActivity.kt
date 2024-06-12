package com.example.idollapp.address

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar

class ManagerAddressActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, ManagerAddressActivity::class.java)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {
        val repository = rememberAddressRepository()
        val coroutineScope = rememberCoroutineScope()
        val address = remember {
            mutableStateListOf<UserAddress>()
        }
        LaunchedEffect(key1 = Unit) {
            repository.getAddressList().collect {
                address.clear()
                address.addAll(it)
            }
        }

        Column {

            TitleBar("管理收货地址")
            Box(modifier = Modifier.fillMaxSize()) {

                if (address.isEmpty()) {

                    Text("暂无收货地址")


                } else {

                    ManagerAddressScreen(addresses = address) {
                        // start edit address activity
                        startActivity(EditAddressActivity.start(context, it.id.toInt()))
                    }
                }

                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = {
                        context.startActivity(AddAddressActivity.start(context))
                    }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "")
                }
            }

        }


    }
}
