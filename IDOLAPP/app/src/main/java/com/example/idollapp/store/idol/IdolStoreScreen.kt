package com.example.idollapp.store.idol

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsDetailsActivity
import com.example.idollapp.store.GoodsItem
import com.example.idollapp.store.model.BannerEntity
import com.example.idollapp.store.model.IdolEntity
import com.example.idollapp.test.generateBanners
import com.example.idollapp.test.generateGoods
import com.example.idollapp.test.generateIdols
import com.example.idollapp.ui.base.paging.GenericPagingGridScreen
import com.example.idollapp.ui.base.paging.GenericPagingRowListScreen
import com.example.idollapp.ui.theme.AppTheme
import com.example.idollapp.ui.view.BannerView
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.ui.view.ViewSpacer
import com.example.idollapp.ui.view.rememberBooleanState
import kotlinx.coroutines.flow.flow

@Preview
@Composable
private fun PreviewIdolStore() {
    val goodsList =
        flow { emit(PagingData.from(generateGoods())) }.collectAsLazyPagingItems()
    val idols =
        flow { emit(PagingData.from(generateIdols())) }.collectAsLazyPagingItems()
    AppTheme {
        IdolStoreScreen(
            modifier = Modifier.fillMaxSize(),
            banners = generateBanners(),
            idols = idols,
            selectedIdol = null,
            onSelectedIdol = {

            },
            goodsList = goodsList
        )
    }
}

@Composable
fun IdolStoreScreen(modifier: Modifier = Modifier) {
    val viewModel = createIdolStoreViewModel()
    val idols = viewModel.idols.collectAsLazyPagingItems()
    val banners = viewModel.banners.collectAsState(initial = listOf()).value
    val idolEntity = viewModel.selectedIdol.collectAsState(initial = null).value
    val goodsPagingData = viewModel.goodsList.collectAsLazyPagingItems()
    LaunchedEffect(key1 = idols.itemCount) {
        idols.itemSnapshotList.getOrNull(0)?.let {
            viewModel.setIdol(it)
        }
    }
    IdolStoreScreen(
        modifier = modifier,
        banners = banners,
        idols = idols,
        selectedIdol = idolEntity,
        onSelectedIdol = { idol ->
            viewModel.setIdol(idol)
        },
        goodsList = goodsPagingData
    )
}

@Composable
private fun IdolStoreScreen(
    modifier: Modifier = Modifier,
    idols: LazyPagingItems<IdolEntity>,
    selectedIdol: IdolEntity?,
    onSelectedIdol: (IdolEntity) -> Unit,
    banners: List<BannerEntity>,
    goodsList: LazyPagingItems<Goods>,
) {

    Box(modifier = modifier) {

        val context = LocalContext.current
        GenericPagingGridScreen(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            items = goodsList,
        ) {

            if (banners.isNotEmpty()) {
                item(span = { GridItemSpan(2) }) {
                    BannerView(modifier = Modifier.fillMaxWidth(), data = banners.map {
                        {
                            LoadingImage(
                                model = it.pic,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(16f / 9f),
                            )
                        }
                    })
                }
            }

            item(span = { GridItemSpan(2) }) {
                Text(
                    text = "热销商品", modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5F5F5))
                        .padding(10.dp)
                )
            }

            items(goodsList.itemCount) {
                val item = goodsList[it]
                if (item != null) {
                    GoodsItem(item) {
                        context.startActivity(GoodsDetailsActivity.start(context, it.id))
                    }
                }
            }
        }
        FloatIdolButton(idols, selectedIdol, onSelectedIdol)
    }
}

@Composable
fun BoxScope.FloatIdolButton(
    idols: LazyPagingItems<IdolEntity>,
    selectedIdol: IdolEntity?,
    onSelectedIdol: (IdolEntity) -> Unit
) {

    val idolsShow = rememberBooleanState()
    Card(
        Modifier
            .padding(16.dp)
            .align(Alignment.BottomEnd),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.tertiaryContainer),
    ) {
        AnimatedVisibility(visible = idolsShow.get()) {
            IdolList(
                modifier = Modifier.fillMaxWidth(),
                idols,
                selectedIndex = selectedIdol,
                onClick = { index, entity ->
                    onSelectedIdol(entity)
                    idolsShow.beFalse()
                })
        }
        AnimatedVisibility(visible = !idolsShow.get()) {
            Column(modifier = Modifier.clickable { idolsShow.toggle() }) {
                selectedIdol?.let { Idol(it) }
            }
        }
    }
}

@Composable
private fun IdolList(
    modifier: Modifier,
    idolEntities: LazyPagingItems<IdolEntity>,
    selectedIndex: IdolEntity?,
    onClick: (Int, IdolEntity) -> Unit
) {
    GenericPagingRowListScreen(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        items = idolEntities,
    ) {
        items(idolEntities.itemCount) { index ->
            val item = idolEntities[index]
            if (item != null) {
                ElevatedCard(
                    modifier = Modifier.clickable { onClick(index, item) },
                    colors = CardDefaults.cardColors(if (selectedIndex?.id == item.id) MaterialTheme.colorScheme.tertiaryContainer else Color.White),
                ) {
                    Idol(item)
                }
            }
        }
    }

}

@Composable
private fun ColumnScope.Idol(item: IdolEntity) {
    LoadingImage(
        model = item.avatar,
        modifier = Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 8.dp)
            .size(52.dp)
            .clip(CircleShape)
            .align(Alignment.CenterHorizontally),
    )
    ViewSpacer(size = 8)
    Text(
        text = item.name, modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(bottom = 8.dp)
            .align(Alignment.CenterHorizontally)
    )
}


