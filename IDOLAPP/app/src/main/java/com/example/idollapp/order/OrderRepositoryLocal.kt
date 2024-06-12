package com.example.idollapp.order

import com.example.idollapp.database.OrderDao

class OrderRepositoryLocal(private val orderDao: OrderDao) : IOrderRepository {

    override suspend fun getOrderDetailInfo(orderId: String): OrderDetailInfoVo {

        val orderDetailInfo = orderDao.getOrderDetailInfo(orderId)
        val order = orderDetailInfo.order
        val items = orderDetailInfo.items
        val address = orderDetailInfo.address
        val sumOfPrice = items.sumOf { it.price.toDouble() * it.quantity }
        val sumOfQuantity = items.sumOf { it.quantity }

        return OrderDetailInfoVo(
            orderId = order.id.toString(),
            title = order.title,
            payAmount = sumOfPrice,
            quantity = sumOfQuantity,
            receiverDetailAddress = address?.address ?: "",
            receiverName = address?.name ?: "",
            receiverPhone = address?.phone ?: "",
            orderSn = order.orderSn,
            paymentTime = order.paymentTime.toString(),
            status = IOrderStatus.covert(order.status),
            goodsList = items.map { it.toGoods() }
        )

    }

}