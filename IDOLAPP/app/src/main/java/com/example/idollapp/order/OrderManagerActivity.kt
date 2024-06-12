package com.example.idollapp.order

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.database.AppDatabase
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.base.paging.GenericPagingListScreen
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.ui.view.TitleBar
import com.example.idollapp.ui.view.rememberBooleanState
import kotlinx.coroutines.launch

class OrderManagerActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context): Intent {
            val intent = Intent(context, OrderManagerActivity::class.java)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val orderSource = remember {
            OrderSourceLocal(AppDatabase.getInstance(context).orderDao())
        }

        val pagingItems =
            orderSource.getOrderList(IOrderStatus.All).collectAsLazyPagingItems()

        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.background(Color(0xFFF0EFEF))) {

            TitleBar(title = "订单管理")

            OrderListScreen(orderList = pagingItems) { item, status ->
                coroutineScope.launch {
                    orderSource.updateStatus(item.id, status)
                        .collect {
                            if (it) {
                                pagingItems.refresh()
                            }
                        }
                }
            }

        }

    }

    @Composable
    fun OrderListScreen(
        modifier: Modifier = Modifier,
        orderList: LazyPagingItems<OrderListItem>,
        onStatusChange: (OrderListItem, IOrderStatus) -> Unit
    ) {
        val context = LocalContext.current
        GenericPagingListScreen(modifier = modifier, items = orderList) {
            items(orderList.itemCount) {
                val item = orderList[it]
                if (item != null) {
                    OrderItem(modifier = Modifier.clickable {
                        context.startActivity(OrderDetailsActivity.start(context, item.id))
                    }, item = item, onStatusChange = onStatusChange)
                }
            }
        }
    }


    @Composable
    fun OrderItem(
        modifier: Modifier,
        item: OrderListItem,
        onStatusChange: (OrderListItem, IOrderStatus) -> Unit
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
                    model = item.imageUrl, modifier = Modifier.size(86.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row {
                        Text(
                            text = item.title,
                            maxLines = 2,
                            minLines = 2,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.weight(1f)
                        )
                        Text(text = item.status.title())
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "运费 : ${item.freight} ",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                    Text(
                        text = "实付 : ${item.price} ",
                        fontSize = 14.sp,
                        modifier = Modifier.align(Alignment.End)
                    )
                }
            }
            Row {
                val popupState = rememberBooleanState()
                Spacer(modifier = Modifier.weight(1f))
                Button(onClick = {
                    popupState.beTrue()
                }) {
                    Text(text = "状态修改")
                }
                if (popupState.get()) {
                    Popup(
                        alignment = Alignment.Center,
                        onDismissRequest = { popupState.beFalse() }
                    ) {
                        StatusSelectionPopup(onDismiss = {
                            popupState.beFalse()
                        }, onStatusSelected = {
                            onStatusChange(item, it)
                            popupState.beFalse()
                        })
                    }

                }
            }
        }
    }


    @Composable
    fun StatusSelectionPopup(
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit,
        onStatusSelected: (IOrderStatus) -> Unit
    ) {
        val statusOptions = listOf(
            IOrderStatus.WaitPay,
            IOrderStatus.WaitSend,
            IOrderStatus.WaitReceive,
            IOrderStatus.WaitComment
        )
        var selectedOption by remember { mutableStateOf(statusOptions[0]) }

        Box(
            modifier = modifier
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column {
                statusOptions.forEach { status ->
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedOption = status
                                onStatusSelected(selectedOption)
                            }
                            .padding(8.dp)) {
                        RadioButton(selected = (status == selectedOption), onClick = {
                        })
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = status.title())
                    }
                }
            }
        }
    }

}
