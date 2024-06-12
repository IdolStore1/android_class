package com.example.idollapp.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsDetailsActivity
import com.example.idollapp.ui.base.paging.GenericPagingGridScreen
import com.example.idollapp.ui.view.LoadingImage


@Composable
fun GoodsListScreen(
    modifier: Modifier = Modifier,
    goodsList: LazyPagingItems<Goods>,
) {
    val context = LocalContext.current
    GenericPagingGridScreen(
        modifier = modifier,
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        items = goodsList,
    ) {
        items(goodsList.itemCount) {
            val item = goodsList[it]
            if (item != null) {
                GoodsItem(item) {
                    context.startActivity(GoodsDetailsActivity.start(context, it.id))
                }
            }
        }
    }

}


@Composable
fun GoodsItem(goods: Goods, onClick: (Goods) -> Unit) {

    Card(colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.clickable { onClick(goods) }) {
        Column(modifier = Modifier.clip(RoundedCornerShape(10.dp))) {
            LoadingImage(
                model = goods.pic,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = goods.name,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                minLines = 2
            )
            Spacer(modifier = Modifier.size(10.dp))
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            ) {
                Text(text = "￥", fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
                Text(
                    text = goods.price.toString(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Text(
                text = "已售 ${goods.sold} 件", fontSize = 11.sp, color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }

}
