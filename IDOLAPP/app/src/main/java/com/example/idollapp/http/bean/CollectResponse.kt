package com.example.idollapp.http.bean


data class CheckFavoritesResponse(
    val favorites: List<FavoriteItem>,
    val code: Int
)

data class FavoriteItem(
    val id: Int,
    val defaultPrice: Int,
    val count: Int,
    val spuName: String,
    val defaultImgUrl: String
)