package com.example.idollapp.cart

import com.example.idollapp.database.ShoppingCart
import com.example.idollapp.database.ShoppingCartDao
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.Goods
import com.example.idollapp.goods.GoodsDetailsSku
import com.example.idollapp.goods.toCartItem
import com.example.idollapp.goods.toDb
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CartRepositoryLocal(private val shoppingCartDao: ShoppingCartDao) : ICartRepository {
    val userId = AppUserManager.instance().getUserInfo()?.id ?: ""

    override suspend fun fetchCartItems(): Flow<List<CartItem>> {
        return shoppingCartDao
            .queryCartItems(userId)
            .map { it.map { it.toCartItem() } }
    }

    override suspend fun addToCart(cartItem: CartItem) {
        val goods = cartItem.item
        shoppingCartDao.findByGoodsIdUserId(cartItem.item.id, userId)
            ?.let { it.copy(productQuantity = it.productQuantity + 1) }
            ?.let { shoppingCartDao.updateCartItems(listOf(it)) }
            ?: let {
                shoppingCartDao.insertCartItems(
                    listOf(
                        ShoppingCart(
                            0, userId,
                            goods.id, goods.name, goods.price, goods.pic, 1,
//                            cartItem.skuName,
                        )
                    )
                )
            }
    }

    override suspend fun addToCart(goods: Goods, goodsDetailsSku: GoodsDetailsSku) {
        addToCart(CartItem(0, goods, 1, goodsDetailsSku.name))
    }

    override suspend fun removeFromCart(cartItem: CartItem) {
        shoppingCartDao.findByGoodsIdUserId(cartItem.item.id, userId)
            ?.let { it.copy(productQuantity = it.productQuantity - 1) }
            ?.let {
                if (it.productQuantity <= 0) {
                    shoppingCartDao.deleteCartItems(listOf(it))
                } else {
                    shoppingCartDao.updateCartItems(listOf(it))
                }
            }
    }

    override suspend fun removeGoodsFromCart(cart: CartItem) {
        shoppingCartDao.deleteCartItems(listOf(cart.toDb(userId)))
    }

}