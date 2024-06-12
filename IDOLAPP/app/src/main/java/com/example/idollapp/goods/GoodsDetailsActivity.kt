package com.example.idollapp.goods

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.idollapp.settlement.SettlementActivity
import com.example.idollapp.ui.base.BaseComposeActivity
import com.example.idollapp.ui.view.TitleBar
import com.example.idollapp.ui.view.shotToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class GoodsDetailsActivity : BaseComposeActivity() {

    companion object {
        fun start(context: Context, goodsId: String): Intent {
            val intent = Intent(context, GoodsDetailsActivity::class.java)
            intent.putExtra("goodsId", goodsId)
            return intent
        }
    }

    @Composable
    override fun RenderContent() {

        val viewModel = createGoodsViewModel()
        val goodsId = intent.getStringExtra("goodsId")
        LaunchedEffect(key1 = goodsId) {
            goodsId?.let {
                viewModel.setGoodsId(it)
            }
        }

        val details = viewModel.goods.collectAsState(initial = null).value
        val skuList = viewModel.goodsSku.collectAsState(initial = listOf()).value
        val goodsFavorite = viewModel.goodsFavorite.collectAsState().value
        val selectedSku = remember { mutableStateOf(skuList.firstOrNull()) }
        val coroutineScope = rememberCoroutineScope()

        Column(modifier = Modifier.navigationBarsPadding()) {

            TitleBar(title = "商品详情")

            details?.let {

                ProductDetailScreen(
                    images = details.pic,
                    title = details.name,
                    price = "¥${String.format(Locale.getDefault(), "%.2f", details.price)}",
                    sales = details.sold,
                    favorite = goodsFavorite,
                    description = details.desc,
                    reviews = emptyList(),
                    recommendedProducts = emptyList(),
                    models = skuList,
                    selectedModel = selectedSku.value,
                    onModelSelected = { selectedSku.value = it },
                    onAddToCartClick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            selectedSku.value?.let {
                                viewModel.addToCartClick(it).collect {
                                    withContext(Dispatchers.Main) {
                                        if (it) {
                                            shotToast(context, "添加成功")
                                        } else {
                                            shotToast(context, "添加失败")
                                        }
                                    }
                                }
                            } ?: let {
                                withContext(Dispatchers.Main) {
                                    shotToast(context, "请选择商品规格")
                                }
                            }
                        }
                    },
                    onBuyNowClick = {
                        context.startActivity(SettlementActivity.start(context, goodsId = goodsId))
                    },
                    onProductClick = { },
                    onFavoriteClick = { viewModel.onClickFavorite() }
                )
            } ?: let {
                CircularProgressIndicator()
            }
        }
    }

}
