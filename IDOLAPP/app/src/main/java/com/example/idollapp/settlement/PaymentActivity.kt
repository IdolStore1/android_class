package com.example.idollapp.settlement

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.order.IOrderStatus
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar
import com.example.idollapp.ui.view.showShotToast
import kotlinx.coroutines.launch

class PaymentActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, orderId: String): Intent {
            val intent = Intent(context, PaymentActivity::class.java)
            intent.putExtra("orderId", orderId)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val orderDao = remember { AppDatabase.getInstance(context).orderDao() }
        val orderId = remember { intent.getStringExtra("orderId") }
        val orderSn = remember { mutableStateOf("") }
        val orderTotalAmount = remember { mutableDoubleStateOf(0.0) }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = intent) {
            intent.getStringExtra("orderId")?.let {
                val order = orderDao.findOrderById(it)
                val orderItems = orderDao.findOrderItemsById(it)
                orderSn.value = order?.orderSn ?: ""
                orderTotalAmount.doubleValue =
                    orderItems.sumOf { it.price.toDouble() * it.quantity }
            }
        }

        Column(modifier = Modifier.navigationBarsPadding()) {
            TitleBar(title = "支付订单")
            OrderPaymentScreen(
                orderSn = orderSn.value,
                totalAmount = orderTotalAmount.doubleValue
            ) {
                coroutineScope.launch {
                    orderId?.let {
                        val order = orderDao.findOrderById(it)
                        order?.copy(
                            status = IOrderStatus.WaitSend.value(),
                            paymentTime = System.currentTimeMillis()
                        )?.let {
                            orderDao.update(it)
                        }
                        showShotToast("支付成功")
                    }
                    payed()
                }
            }
        }
    }

    private fun payed() {
        setResult(RESULT_OK)
        finish()
    }

}
