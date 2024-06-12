package com.example.idollapp.order

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.R
import com.example.idollapp.test.generateGoods
import com.example.idollapp.utils.toFormatString

@Preview
@Composable
private fun PreviewOrderDetailsScreen() {
    OrderDetailScreen(
        orderDetail = OrderDetailInfoVo(
            orderId = "1",
            title = "",
            payAmount = 199.98,
            quantity = 19,
            receiverDetailAddress = "广东省广州市海珠区XX路XX号",
            receiverName = "张三",
            receiverPhone = "13812345678",
            orderSn = "2023101010101010",
            paymentTime = "2023-10-10 10:10:10",
            status = IOrderStatus.All,
            goodsList = generateGoods()
        )
    )
}


@Composable
fun OrderDetailScreen(
    modifier: Modifier = Modifier,
    orderDetail: OrderDetailInfoVo
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            OrderInfoCard(orderDetail)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            ShippingInfoCard(orderDetail)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            PaymentInfoCard(orderDetail)
        }
    }
}

@Composable
fun OrderInfoCard(orderDetail: OrderDetailInfoVo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = R.drawable.baseline_image_24), // 替换为实际的图片资源
                contentDescription = orderDetail.orderId,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${orderDetail.title}",
                maxLines = 5,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "总价: ${orderDetail.payAmount.toFormatString()}",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.align(alignment = Alignment.End)
            )
        }
    }
}

@Composable
fun ShippingInfoCard(orderDetail: OrderDetailInfoVo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "地址", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "收货人  : ${orderDetail.receiverName}")
            Text(text = "手机号  : ${orderDetail.receiverPhone}")
            Text(text = "地址    : ${orderDetail.receiverDetailAddress}")
        }
    }
}

@Composable
fun PaymentInfoCard(orderDetail: OrderDetailInfoVo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "支付信息", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "订单号     :  ${orderDetail.orderSn}")
            Text(text = "支付时间   : ${orderDetail.paymentTime}")
        }
    }
}