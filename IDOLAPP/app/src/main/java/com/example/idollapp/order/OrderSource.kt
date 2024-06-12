package com.example.idollapp.order

import androidx.paging.PagingData
import com.example.idollapp.database.OrderDao
import com.example.idollapp.database.OrderWithItems
import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.bean.OrderInfoResponse
import com.example.idollapp.http.repository.OrderRepository
import com.example.idollapp.test.generateOrderItems
import com.example.idollapp.ui.base.paging.GenericPagingRepository
import com.example.idollapp.user.usermanager.AppUserManager
import com.example.idollapp.utils.toFormatString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.Locale

interface IOrderSource {

    fun getOrderList(status: IOrderStatus): Flow<PagingData<OrderListItem>>

    fun updateStatus(orderId: String, status: IOrderStatus): Flow<Boolean>

}

class OrderSourceLocal(private val orderDao: OrderDao) : IOrderSource {
    override fun getOrderList(status: IOrderStatus): Flow<PagingData<OrderListItem>> {
        val userInfo = AppUserManager.instance().getUserInfo()
        return GenericPagingRepository { page, size ->
            val userId = userInfo?.id ?: ""
            val response: List<OrderWithItems> = withContext(Dispatchers.IO) {
                if (status == IOrderStatus.All) {
                    orderDao.getOrdersWithItemsForUser(userId)
                } else {
                    orderDao.getOrdersWithItemsForUser(userId, status.value())
                }
            }
            response.map {
                val payamount = it.items.sumOf { it.price.toDouble() * it.quantity }
                val order = it.order
                OrderListItem(
                    id = "${order.id}",
                    sn = order.orderSn,
                    title = order.title,
                    price = payamount.toFormatString(),
                    imageUrl = "",
                    freight = (payamount * 0.1).toFormatString(),
                    remark = order.remark,
                    status = IOrderStatus.covert(order.status)
                )
            }
        }.getPagingData()
    }

    override fun updateStatus(orderId: String, status: IOrderStatus): Flow<Boolean> {
        return flow {
            Timber.d("updateStatus : $orderId $status")
            orderDao.findOrderById(orderId)?.let {
                orderDao.update(it.copy(status = status.value()))
            }
            emit(true)
        }
    }
}

class OrderSourceDebug : IOrderSource {
    override fun getOrderList(status: IOrderStatus): Flow<PagingData<OrderListItem>> {
        return flow {
            emit(PagingData.from(generateOrderItems()))
        }
    }

    override fun updateStatus(orderId: String, status: IOrderStatus): Flow<Boolean> {
        return flow { emit(true) }
    }
}

class OrderSourceRemote(private val repository: OrderRepository) : IOrderSource {
    override fun getOrderList(status: IOrderStatus): Flow<PagingData<OrderListItem>> {
        val userInfo = AppUserManager.instance().getUserInfo()
        return GenericPagingRepository { page, size ->
            val userId = userInfo?.id
            val response: Result<ApiResponse<List<OrderInfoResponse>>> = when (status) {
                IOrderStatus.All -> repository.selectAllOrders(userId)
                IOrderStatus.WaitPay -> repository.selectUnpaidOrders(userId)
                IOrderStatus.WaitSend -> repository.selectUnshippedOrders(userId)
                IOrderStatus.WaitReceive -> repository.selectShippedOrders(userId)
                IOrderStatus.WaitComment -> repository.selectReceivedOrders(userId)
            }
            val orThrow = response.getOrThrow()
            if (orThrow.isSuccess) {
                orThrow.data.map {
                    OrderListItem(
                        id = "${it.orderId}",
                        sn = it.orderSn,
                        title = it.orderSn,
                        price = String.format(Locale.getDefault(), "%.2f", it.payAmount),
                        imageUrl = "",
                        freight = String.format(Locale.getDefault(), "%.2f", it.payAmount),
                        remark = "",
                        status = status
                    )
                }
            } else {
                emptyList()
            }
        }.getPagingData()
    }

    override fun updateStatus(orderId: String, status: IOrderStatus): Flow<Boolean> {
        return flow { emit(true) }
    }
}