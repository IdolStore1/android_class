package com.example.idollapp.order

import com.example.idollapp.test.generateGoods

class OrderRepositoryDebug : IOrderRepository {
    override suspend fun getOrderDetailInfo(orderId: String): OrderDetailInfoVo {
        return OrderDetailInfoVo(
            orderId = "order.id.toString()",
            title = "order.title",
            payAmount = 2304.3,
            quantity = 23,
            receiverDetailAddress = "address.address",
            receiverName = "address.name",
            receiverPhone = "address.phone",
            orderSn = "order.orderSn",
            paymentTime = "order.paymentTime.toString()",
            status = IOrderStatus.All,
            goodsList = generateGoods()
        )
    }
}