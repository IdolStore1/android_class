package com.example.idollapp.goods


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idollapp.test.generateGoods
import com.example.idollapp.test.generateGoodsDetailsComment
import com.example.idollapp.test.generateGoodsDetailsImgs
import com.example.idollapp.test.generateGoodsDetailsSku
import com.example.idollapp.ui.view.LoadingImage

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    ProductDetailScreen(
        images = generateGoodsDetailsImgs(),
        title = "商品标题",
        price = "¥99.99",
        sales = 500,
        favorite = true,
        description = "这是商品的详细描述。这个商品非常好，功能齐全，质量可靠，价格合理。买它就对了！",
        reviews = generateGoodsDetailsComment(),
        recommendedProducts = generateGoods(),
        models = generateGoodsDetailsSku(),
        selectedModel = GoodsDetailsSku(1.toString(), "Model 1", "8.8f", listOf()),
        onModelSelected = { /* Model Selected */ },
        onAddToCartClick = { /* Add to Cart */ },
        onBuyNowClick = { /* Buy Now */ },
        onProductClick = { /* Product Clicked */ },
        onFavoriteClick = { /* Favorite Click */ }
    )
}

@Composable
fun ProductDetailScreen(
    images: List<GoodsDetailsImage>,
    title: String,
    price: String,
    sales: Int,
    favorite: Boolean,
    description: String,
    reviews: List<GoodsComment>,
    recommendedProducts: List<Goods>,
    models: List<GoodsDetailsSku>,
    selectedModel: GoodsDetailsSku?,
    onModelSelected: (GoodsDetailsSku) -> Unit,
    onAddToCartClick: () -> Unit,
    onBuyNowClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onFavoriteClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .weight(1f)
        ) {
            ProductImageCarousel(images)
            ProductTitleAndPrice(title, price, sales)
            ModelSelection(models, selectedModel, onModelSelected)
            ProductDescription(description)
            if (reviews.isNotEmpty()) {
                ProductReviews(reviews)
            }
            if (recommendedProducts.isNotEmpty()) {
                RecommendedProducts(recommendedProducts, onProductClick)
            }
        }

        Row(modifier = Modifier.background(Color(0xFFF5F5F5))) {
            FavoriteButton(favorite) {
                onFavoriteClick()
            }
            AddToCartAndBuyButtons(onAddToCartClick, onBuyNowClick)
        }
    }
}

@Composable
fun ProductImageCarousel(images: List<GoodsDetailsImage>) {
    var selectedImageIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
    ) {
        LoadingImage(
            model = images[selectedImageIndex].imgUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            itemsIndexed(images) { index, url ->
                LoadingImage(
                    model = url.imgUrl,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(50.dp)
                        .clickable { selectedImageIndex = index }
                )
            }
        }
    }
}

@Composable
fun ProductTitleAndPrice(title: String, price: String, sales: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = price,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "销量: $sales 件",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ModelSelection(
    models: List<GoodsDetailsSku>,
    selectedModel: GoodsDetailsSku?,
    onModelSelected: (GoodsDetailsSku) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "选择规格", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.SpaceEvenly) {
            models.forEach { model ->
                item {
                    TextButton(
                        modifier = Modifier.padding(horizontal = 3.dp),
                        onClick = { onModelSelected(model) },
                        colors = if (model.id == selectedModel?.id) ButtonDefaults.textButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ) else ButtonDefaults.textButtonColors(),
                        shape = RoundedCornerShape(5.dp),
                        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.tertiaryContainer)
                    ) {
                        Text(
                            text = model.name,
                            color = if (model == selectedModel) Color.White else Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProductDescription(description: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "商品描述",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ProductReviews(reviews: List<GoodsComment>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "用户评价",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        reviews.forEach { review ->
            Text(
                text = review.user + ":" + review.content,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun AddToCartAndBuyButtons(onAddToCartClick: () -> Unit, onBuyNowClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onAddToCartClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "加入购物车", color = Color.White)
        }
        Button(
            onClick = onBuyNowClick,
            colors = ButtonDefaults.buttonColors()
        ) {
            Text(text = "立即购买", color = Color.White)
        }
    }
}

@Composable
fun FavoriteButton(check: Boolean, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onFavoriteClick) {
            if (check) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "",
                    tint = Color.Red
                )
            } else {
                Icon(imageVector = Icons.Default.FavoriteBorder, contentDescription = "")
            }
        }
    }
}

@Composable
fun RecommendedProducts(recommendedProducts: List<Goods>, onProductClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "推荐商品",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            recommendedProducts.forEachIndexed { index, goods ->
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(100.dp)
                        .clickable { onProductClick(index) }
                ) {
                    LoadingImage(
                        model = goods.pic,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                    Text(
                        text = "推荐商品$index",
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

