package com.example.idollapp.settlement

import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.toCartItem
import com.example.idollapp.test.generateCartItems
import com.example.idollapp.test.generateGoodsDetails
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettlementRepositoryLocalDebug(private val repository: ISettlementRepository): ISettlementRepository by repository {

    override  fun loadCartByGoodsId(goodsId: String): Flow<LoadingData<List<CartItem>>> =
        flow {
            emit(LoadingData.Success(generateCartItems()))
        }


}