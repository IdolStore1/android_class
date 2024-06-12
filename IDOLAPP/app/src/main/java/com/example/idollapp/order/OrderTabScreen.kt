package com.example.idollapp.order

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.settlement.PaymentActivity
import com.example.idollapp.ui.theme.BackgroundColorFAFAFA
import com.example.idollapp.ui.view.SpacerDivider
import com.example.idollapp.ui.view.showShotToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val tabs = listOf(
    IOrderStatus.All,
    IOrderStatus.WaitPay,
    IOrderStatus.WaitSend,
    IOrderStatus.WaitReceive,
    IOrderStatus.WaitComment,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderTabScreen(modifier: Modifier = Modifier) {

    val orderListViewModel = createOrderListViewModel()

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState { tabs.size }
    val context = LocalContext.current

    Column(modifier = modifier) {
        TabRow(selectedTabIndex = pagerState.pageCount) {
            tabs.forEachIndexed { index, title ->
                val selected = pagerState.targetPage == index
                Tab(
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(index);
                        }
                    },
                    text = {
                        Text(
                            text = title.title(),
                            fontSize = 12.sp,
                            modifier = Modifier.scale(if (selected) 1.3f else 1.0f)
                        )
                    },
                )
            }
        }
        SpacerDivider()
        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) {
            val status = tabs[it]
            val lazyPagingItems =
                orderListViewModel.getOrderList(status).collectAsLazyPagingItems()
            OrderListScreen(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundColorFAFAFA),
                iOrderStatus = status,
                orderList = lazyPagingItems,
                onItemButton = {
                    when (it.status) {
                        IOrderStatus.WaitPay -> {
                            context.startActivity(PaymentActivity.start(context, it.id))
                        }

                        IOrderStatus.WaitSend -> {
                            context.showShotToast("催促成功")
                        }

                        IOrderStatus.WaitReceive -> {
                            coroutineScope.launch(Dispatchers.IO) {
                                orderListViewModel.updateReceived(it).collect {
                                    withContext(Dispatchers.Main) {
                                        if (it) {
                                            context.showShotToast("收货成功")
                                        } else {
                                            context.showShotToast("收货失败")
                                        }
                                    }
                                    delay(1000)
                                    lazyPagingItems.refresh()
                                }
                            }
                        }

                        IOrderStatus.WaitComment -> {
                            context.showShotToast("未实现")
                        }

                        else -> {
                        }
                    }
                }
            )
        }

    }
}