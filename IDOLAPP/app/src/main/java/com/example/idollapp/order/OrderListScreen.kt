package com.example.idollapp.order

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.test.generateOrderItems
import com.example.idollapp.ui.base.paging.GenericPagingListScreen
import com.example.idollapp.ui.view.LoadingImage
import kotlinx.coroutines.flow.flow

@Preview
@Composable
private fun PreviewOrderList() {
    val collectAsLazyPagingItems =
        flow { emit(PagingData.from(generateOrderItems())) }.collectAsLazyPagingItems()
    OrderListScreen(
        Modifier.fillMaxSize(),
        IOrderStatus.All,
        orderList = collectAsLazyPagingItems,
        {})
}

@Composable
fun OrderListScreen(
    modifier: Modifier = Modifier,
    iOrderStatus: IOrderStatus,
    orderList: LazyPagingItems<OrderListItem>,
    onItemButton: (OrderListItem) -> Unit
) {
    val context = LocalContext.current
    GenericPagingListScreen(modifier = modifier, items = orderList) {
        items(orderList.itemCount) {
            val item = orderList[it]
            if (item != null) {
                OrderItem(
                    modifier = Modifier.clickable {
                        context.startActivity(OrderDetailsActivity.start(context, item.id))
                    },
                    collection = item,
                    buttonsLayout = {
                        when (item.status) {
                            IOrderStatus.All -> {}
                            IOrderStatus.WaitComment -> {
                                Button(onClick = { onItemButton(item) }) {
                                    Text(text = "去评论")
                                }
                            }

                            IOrderStatus.WaitPay -> {
                                Button(onClick = { onItemButton(item) }) {
                                    Text(text = "去支付")
                                }
                            }

                            IOrderStatus.WaitReceive -> {
                                Button(onClick = { onItemButton(item) }) {
                                    Text(text = "收货")
                                }
                            }

                            IOrderStatus.WaitSend -> {
                                Button(onClick = { onItemButton(item) }) {
                                    Text(text = "催发货")
                                }
                            }
                        }

                    }
                )
            }
        }
    }
}

@Composable
fun OrderItem(
    modifier: Modifier,
    collection: OrderListItem,
    buttonsLayout: @Composable () -> Unit
) {

    Card(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
        ) {
            LoadingImage(
                model = collection.imageUrl,
                modifier = Modifier.size(86.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = collection.title,
                    maxLines = 2,
                    minLines = 2,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
//                Text(text = "备注 : ${collection.remark} ", fontSize = 14.sp, maxLines = 2)
//                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "运费 : ${collection.freight} ", fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.End)
                )
                Text(
                    text = "实付 : ${collection.price} ", fontSize = 14.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
        Row(modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)) {
            Spacer(modifier = Modifier.weight(1f))
            buttonsLayout()
        }
    }


}








