package com.example.idollapp.settlement

import com.example.idollapp.database.OrderDao
import com.example.idollapp.database.OrderEntity
import com.example.idollapp.database.ShoppingCartDao
import com.example.idollapp.database.UserAddressDao
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.UserAddress
import com.example.idollapp.goods.toCartItem
import com.example.idollapp.goods.toUI
import com.example.idollapp.http.repository.ProductRepository
import com.example.idollapp.order.IOrderStatus
import com.example.idollapp.order.toOrderItem
import com.example.idollapp.ui.base.LoadingData
import com.example.idollapp.user.usermanager.AppUserManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SettlementRepositoryLocal(
    private val address: UserAddressDao,
    private val shoppingCartDao: ShoppingCartDao,
    private val orderDao: OrderDao,
    private val productRepository: ProductRepository
) : ISettlementRepository {
    val userId = AppUserManager.instance().getUserInfo()?.id ?: ""

    override fun loadAddress(): Flow<List<UserAddress>> = flow {
        address.findAddressListByUserId(userId).collect { addressList ->
            emit(addressList.map { it.toUI() })
        }
    }

    override fun loadCartByGoodsId(goodsId: String): Flow<LoadingData<List<CartItem>>> =
        flow {
            val result = productRepository.getSpuInfo(goodsId, userId)
            result.onSuccess {
                if (it.isSuccess) {
                    emit(LoadingData.Success(listOf(it.data.spuInfo.toCartItem())))
                } else {
                    emit(LoadingData.Error(it.message))
                }
            }.onFailure {
                it.printStackTrace()
                emit(LoadingData.Error(it.message ?: "Unknown Error"))
            }
        }

    override fun loadCartFromDB(): Flow<LoadingData<List<CartItem>>> = flow {
        val cartItems = shoppingCartDao.getCartItems(userId)
        if (cartItems.isNotEmpty()) {
            emit(LoadingData.Success(cartItems.map { it.toCartItem() }))
        } else {
            emit(LoadingData.Error("购物车为空"))
        }
    }

    override fun loadCartFromDBGoodsIds(cartIds: List<String>): Flow<LoadingData<List<CartItem>>> {
        return flow {
            val cartItems = shoppingCartDao.getCartItems(userId)
            if (cartItems.isNotEmpty()) {
                emit(LoadingData.Success(
                    cartItems
                        .filter { cartIds.contains(it.id.toString()) }
                        .map { it.toCartItem() }
                ))
            } else {
                emit(LoadingData.Error("购物车为空"))
            }
        }
    }

    override fun submitOrder(cartItems: List<CartItem>, address: UserAddress?): Flow<Int> = flow {
        val orderId = orderDao.createOrder(
            OrderEntity(
                id = 0,
                userId = userId,
                orderSn = System.currentTimeMillis().toString(),
                title = cartItems.joinToString(separator = ",") { it.item.name },
                remark = "",
                status = IOrderStatus.WaitPay.value(),
                addressId = address?.id ?: 0,
                paymentTime = 0,
            )
        )
        val orderItems = cartItems.map { it.toOrderItem(orderId.toInt()) }
        orderDao.insertOrderItems(orderItems)
        emit(orderId.toInt())
    }

    override fun clearShoppingCart(ids: List<String>): Flow<Boolean> = flow {
        shoppingCartDao.deleteAll(userId, ids)
        emit(true)
    }
}