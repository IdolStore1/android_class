package com.example.idollapp.order

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrderDetailsActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, orderId: String): Intent {
            val intent = Intent(context, OrderDetailsActivity::class.java)
                .putExtra("orderId", orderId)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val repository: IOrderRepository =
            remember { OrderRepositoryLocal(AppDatabase.getInstance(context).orderDao()) }

        val orderDetailsInfo = remember {
            mutableStateOf<OrderDetailInfoVo?>(null)
        }
        LaunchedEffect(key1 = intent) {
            withContext(Dispatchers.IO) {
                intent.getStringExtra("orderId")?.let {
                    orderDetailsInfo.value = repository.getOrderDetailInfo(it)
                }
            }
        }
        Column {
            TitleBar(title = "订单详情")
            orderDetailsInfo.value?.let {
                OrderDetailScreen(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    it
                )
            } ?: let {
                CircularProgressIndicator()
            }
        }
    }

}
