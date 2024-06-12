package com.example.idollapp.settlement

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.base.CommonLoadingLayout
import com.example.idollapp.ui.view.TitleBar
import com.example.idollapp.ui.view.shotToast
import kotlinx.coroutines.launch
import timber.log.Timber

class SettlementActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, goodsId: String? = null, goodsIds: String? = null): Intent {
            val intent = Intent(context, SettlementActivity::class.java)
            intent.putExtra("goodsId", goodsId)
            intent.putExtra("goodsIds", goodsIds)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {
        val viewModel = createSettlementViewModel()

        val goodsId = remember { intent.getStringExtra("goodsId") }
        val goodsIds = remember { intent.getStringExtra("goodsIds") }
        Timber.d(" goods id : $goodsId  $goodsIds ")

        LaunchedEffect(key1 = goodsIds) {
            goodsIds?.split(",")?.let {
                viewModel.loadCartFromDB(it)
            }
            goodsId?.let {
                viewModel.loadCartByGoodsId(goodsId)
            }
        }


        val addressList = viewModel.addressList.collectAsState().value
        val selectedAddress = viewModel.selectedAddress.collectAsState().value
        val cartItems = viewModel.goodsList.collectAsState().value
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.navigationBarsPadding()) {

            TitleBar(title = "结算订单")

            CommonLoadingLayout(data = cartItems) {
                val sumOfQuantity = it.sumOf { it.quantity }
                val sumOfPrice = it.sumOf { it.item.price.toDouble() * it.quantity }
                SettlementScreen(
                    totalQuantity = sumOfQuantity,
                    totalPrice = sumOfPrice,
                    addresses = addressList,
                    selectedAddress = selectedAddress,
                    onSelectedAddress = {
                        viewModel.onSelectedAddress(it)
                    },
                    cartItems = it
                ) {
                    coroutineScope.launch {
                        if (selectedAddress == null) {
                            shotToast(context, "请先添加地址")
                        } else {
                            viewModel.submitOrder(it).collect {
                                startActivity(PaymentActivity.start(context, it.toString()))
                                finish()
                                if (goodsId == null || goodsIds != null) {
                                    viewModel.clearShoppingCart(goodsIds?.split(",") ?: listOf())
                                        .collect {
                                            Timber.d(" clear shopping cart . ")
                                        }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
