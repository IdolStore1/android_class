package com.example.idollapp.store.index

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsCategory
import com.example.idollapp.search.SearchBarLayout
import com.example.idollapp.search.SearchResultActivity
import com.example.idollapp.store.GoodsListScreen
import com.example.idollapp.test.generateCategories
import com.example.idollapp.test.generateGoods
import com.example.idollapp.ui.theme.AppTheme
import com.example.idollapp.ui.view.SpacerDivider
import kotlinx.coroutines.flow.flow

@Preview
@Composable
private fun PreviewIndexStore() {
    val collectAsLazyPagingItems =
        flow { emit(PagingData.from(generateGoods())) }.collectAsLazyPagingItems()
    AppTheme {
        IndexStoreScreen(
            modifier = Modifier.fillMaxSize(),
            categories = generateCategories(),
            selectedCategory = null,
            onCategory = {},
            goodsList = collectAsLazyPagingItems
        )
    }
}

@Composable
fun IndexStoreScreen(modifier: Modifier = Modifier) {
    val viewModel = createIndexStoreViewModel()
    val goodsCategories = viewModel.categories.collectAsState(initial = listOf()).value
    val selectedCategory = viewModel.selectedCategory.collectAsState(initial = null).value
    val goodsPagingData = viewModel.goodsList.collectAsLazyPagingItems()
    Box(modifier = modifier.fillMaxSize()) {
        IndexStoreScreen(
            modifier = Modifier.fillMaxSize(),
            categories = goodsCategories,
            selectedCategory = selectedCategory,
            onCategory = { viewModel.setCategory(it) },
            goodsList = goodsPagingData
        )
    }
}

@Composable
private fun IndexStoreScreen(
    modifier: Modifier = Modifier,
    categories: List<GoodsCategory>,
    selectedCategory: GoodsCategory?,
    onCategory: (GoodsCategory) -> Unit,
    goodsList: LazyPagingItems<Goods>,
) {

    val context = LocalContext.current
    Column(modifier = modifier) {
        // search
        SearchBarLayout {
            context.startActivity(SearchResultActivity.start(context, it))
        }

        if (categories.isNotEmpty()) {

            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            ) {
                itemsIndexed(categories) { index, category ->
                    Box(modifier = Modifier.padding(3.dp)) {
                        TextButton(
                            onClick = {
                                //  onClick(index, category)
                                onCategory(category)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selectedCategory?.id == category.id) MaterialTheme.colorScheme.surface else Color.Transparent,
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = category.name, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        SpacerDivider()

        GoodsListScreen(modifier = Modifier.fillMaxSize(), goodsList = goodsList)

    }

}


