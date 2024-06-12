package com.example.idollapp.cart

import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsDetailsSku
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    suspend fun fetchCartItems(): Flow<List<CartItem>>
    suspend fun addToCart(cartItem: CartItem)
    suspend fun addToCart(goods: Goods,goodsDetailsSku: GoodsDetailsSku)
    suspend fun removeFromCart(cartItem: CartItem)
    suspend fun removeGoodsFromCart(cart: CartItem)
}