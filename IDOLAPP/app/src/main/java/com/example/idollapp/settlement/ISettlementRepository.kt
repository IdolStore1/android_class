package com.example.idollapp.settlement

import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.ui.base.LoadingData
import kotlinx.coroutines.flow.Flow

interface ISettlementRepository {
    fun loadAddress(): Flow<List<UserAddress>>
    fun loadCartByGoodsId(goodsId: String): Flow<LoadingData<List<CartItem>>>
    fun loadCartFromDB(): Flow<LoadingData<List<CartItem>>>
    fun loadCartFromDBGoodsIds(cartIds:List<String>): Flow<LoadingData<List<CartItem>>>
    fun submitOrder(cartItems: List<CartItem>,address: UserAddress?): Flow<Int>
    fun clearShoppingCart(ids: List<String>): Flow<Boolean>
}
