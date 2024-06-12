package com.example.idollapp.order

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBackIconButton

class OrderListActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, OrderListActivity::class.java)
            return intent
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun RenderContent() {
        Column {
            TopAppBar(title = { Text(text = "订单") }, navigationIcon = {
                TitleBackIconButton {
                    finish()
                }
            }, actions = {
                IconButton(onClick = { startActivity(OrderManagerActivity.start(context)) }) {

                }
            })
            OrderTabScreen(modifier = Modifier.fillMaxSize())
        }
    }

}
