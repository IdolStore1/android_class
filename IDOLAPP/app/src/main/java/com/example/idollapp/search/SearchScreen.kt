package com.example.idollapp.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.goods.GoodsDetailsActivity
import com.example.idollapp.ui.base.paging.GenericPagingListScreen
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.ui.view.ViewSpacer


@Preview
@Composable
private fun PreviewSearchItem() {
    SearchItem(goods = SearchItem("", "naepaoifhdaskjf", "82394.24", "")) {

    }
}

@Composable
fun SearchScreen(modifier: Modifier = Modifier, key: String = "") {

    val viewModel = createSearchViewModel()
    val query = viewModel.query.collectAsState(initial = "").value
    val searchResults = viewModel.searchResults.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = key) {
        viewModel.setQuery(key)
    }

    Column(modifier = modifier) {
        // search
        SearchBarLayout(key) {
            viewModel.setQuery(it)
        }
        ViewSpacer(size = 10)

        GenericPagingListScreen(modifier = Modifier.fillMaxSize(), searchResults) {
            items(searchResults.itemCount) {
                val item = searchResults[it]
                if (item != null) {
                    SearchItem(goods = item, onClick = {
                        context.startActivity(GoodsDetailsActivity.start(context, it.id))
                    })
                }
            }
        }

    }
}


@Composable
private fun SearchItem(goods: SearchItem, onClick: (SearchItem) -> Unit) {

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, 8.dp)
            .clickable { onClick(goods) }
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            LoadingImage(
                model = goods.image,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f),
            )
            ViewSpacer(size = 10)
            Column {
                ViewSpacer(size = 10)
                Text(
                    text = goods.name,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    minLines = 2
                )
                ViewSpacer(size = 10)
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                ) {
                    Text(text = "￥", fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
                    Text(
                        text = goods.price.toString(),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }

}
