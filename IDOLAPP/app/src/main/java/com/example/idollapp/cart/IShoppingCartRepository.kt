package com.example.idollapp.cart

interface IShoppingCartRepository {

    fun onAddToCart(goodsId: String, goodsCount: Int)

}