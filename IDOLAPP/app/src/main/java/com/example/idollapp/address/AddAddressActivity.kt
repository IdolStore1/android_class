package com.example.idollapp.address

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.shotToast
import kotlinx.coroutines.launch

class AddAddressActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, AddAddressActivity::class.java)
            return intent
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun RenderContent() {

        val repository = rememberAddressRepository()
        val coroutineScope = rememberCoroutineScope()
        Column {

            TopAppBar(title = {
                Text(text = "添加收货地址")
            })

            EditAddressScreen("", "", "", onAddAddress = { name, phone, address ->
                coroutineScope.launch {

                    repository.addAddress(name, phone, address)
                        .collect {
                            if (it) {
                                shotToast(context, "添加成功！")
                                finish()
                            }else{
                                shotToast(context, "添加失败！")
                            }
                        }
                }
            })

        }

    }
}
