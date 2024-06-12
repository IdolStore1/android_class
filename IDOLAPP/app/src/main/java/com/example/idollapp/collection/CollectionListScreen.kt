package com.example.idollapp.collection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.idollapp.test.generateCollectionItems
import com.example.idollapp.ui.base.paging.GenericPagingListScreen
import com.example.idollapp.ui.view.LoadingImage
import com.example.idollapp.ui.view.SwipeBox
import com.example.idollapp.ui.view.rememberBooleanState
import kotlinx.coroutines.flow.flow

@Preview
@Composable
private fun PreviewCollectionList() {
    val collectAsLazyPagingItems =
        flow { emit(PagingData.from(generateCollectionItems())) }.collectAsLazyPagingItems()
    CollectionListScreen(Modifier.fillMaxSize(), collections = collectAsLazyPagingItems, {}, {})
}

@Composable
fun CollectionListScreen(
    modifier: Modifier = Modifier,
    collections: LazyPagingItems<CollectionGoodsListItem>,
    onRemove: (CollectionGoodsListItem) -> Unit,
    onAddCart: (CollectionGoodsListItem) -> Unit,
) {

    GenericPagingListScreen(modifier = modifier, items = collections) {
        items(collections.itemCount) {
            val item = collections[it]
            if (item != null) {
                val swipeChecked = rememberBooleanState()
                SwipeBox(
                    checked = swipeChecked.get(),
                    onCheckedChange = { swipeChecked.update(it) },
                    bottomContent = {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillParentMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            IconButton(
                                onClick = { onRemove(item);swipeChecked.beFalse() },
                                modifier = Modifier
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    modifier = Modifier
                                )
                            }
                        }
                    }) {
                    CollectionItem(
                        modifier = Modifier,
                        collection = item,
                        onAddCart = { onAddCart(item) }
                    )
                }
            }
        }
    }

}

@Composable
fun CollectionItem(modifier: Modifier, collection: CollectionGoodsListItem, onAddCart: () -> Unit) {

    Card(
        modifier = modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LoadingImage(
                model = collection.imageUrl,
                modifier = Modifier.size(86.dp)
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                Text(text = collection.title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "￥ ${collection.price} ", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
            }
            Icon(
                imageVector = Icons.Outlined.ShoppingCart,
                contentDescription = "",
                modifier = Modifier.clickable {
                    onAddCart()
                })
        }
    }


}








