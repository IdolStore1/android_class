package com.example.idollapp.settlement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.utils.toFormatString

@Preview
@Composable
fun PreviewPaymentScreen(modifier: Modifier = Modifier) {
    OrderPaymentScreen(orderSn = "9462941q047", totalAmount = 234234.1) {

    }
}


@Composable
fun OrderPaymentScreen(orderSn: String, totalAmount: Double, onPay: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                // 显示订单详情
                Text(
                    "订单号 :  ${orderSn}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "支付总额 : ￥ ${totalAmount.toFormatString()}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                )
            }
        }

        // 分隔线
        Spacer(modifier = Modifier.height(16.dp))

        // 支付按钮
        Button(modifier = Modifier.fillMaxWidth(), onClick = { onPay() }) {
            Text("立即支付")
        }
    }
}
