package com.example.idollapp.cart

import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsDetailsSku
import com.example.idollapp.test.generateCartItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DebugCartRepository : ICartRepository {

    private val cartItems = mutableListOf<CartItem>()

    override suspend fun fetchCartItems(): Flow<List<CartItem>> {
        return flow {
            if (cartItems.isEmpty()) {
                generateCartItems().apply { cartItems.addAll(this) }
            } else {
                cartItems
            }
            emit(cartItems)
        }
    }

    override suspend fun addToCart(cartItem: CartItem) {

    }


    override suspend fun addToCart(goods: Goods, goodsDetailsSku: GoodsDetailsSku) {
        val cartItem = cartItems
            .find { it.item == goods }
            ?.let {
                val indexOf = cartItems.indexOf(it)
                cartItems.set(indexOf, it.copy(quantity = it.quantity + 1))
            }
        val maxCartItemId = cartItems.maxOf { it.id }
        if (cartItem == null) {
            cartItems.add(CartItem(maxCartItemId + 1, goods, 1, ""))
        }
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        val oldCartItem = cartItems
            .find { it.item == cartItem.item }
            ?.let {
                val indexOf = cartItems.indexOf(it)
                cartItems.set(indexOf, it.copy(quantity = it.quantity - 1))
            }
        if (oldCartItem == null) {
            cartItems.add(cartItem.copy(id = cartItems.maxOf { it.id } + 1, quantity = 1))
        }
    }

    override suspend fun removeGoodsFromCart(cart: CartItem) {

    }


}