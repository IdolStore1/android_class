package com.example.idollapp.collection

import com.example.idollapp.database.GoodsFavorite

data class CollectionGoodsListItem(
    val id: Int,
    val goodsId: String,
    val title: String,
    val imageUrl: String,
    val price: Float,
)

fun CollectionGoodsListItem.toFavorite():GoodsFavorite{
    return GoodsFavorite(
        id = id,
        goodsId = goodsId,
        userId = "",
        createTime = System.currentTimeMillis(),
        title = title,
        imageUrl = imageUrl,
        price = price
    )
}

