package com.example.idollapp.address

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.shotToast
import kotlinx.coroutines.launch

class EditAddressActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, addressId: Int): Intent {
            val intent = Intent(context, EditAddressActivity::class.java)
                .putExtra("addressId", addressId)
            return intent
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun RenderContent() {

        val repository = rememberAddressRepository()
        val address = remember {
            mutableStateOf<UserAddress?>(null)
        }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = Unit) {
            val addressId = intent.getIntExtra("addressId", 0)
            repository.findAddress(addressId)
                .collect {
                    address.value = it
                }
        }

        Column {

            TopAppBar(title = {
                Text(text = "修改收货地址")
            })

            address.value?.let {
                EditAddressScreen(
                    it.name, it.phone, it.address,
                    onAddAddress = { name, phone, address ->
                        coroutineScope.launch {
                            repository.editAddress(it.id, name, phone, address)
                                .collect {
                                    if (it) {
                                        shotToast(context, "添加成功！")
                                        finish()
                                    } else {
                                        shotToast(context, "添加失败！")
                                    }
                                }
                        }
                    })
            } ?: let {
                CircularProgressIndicator()
            }

        }

    }
}
