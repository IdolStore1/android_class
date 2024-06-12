package com.example.idollapp.order

interface IOrderRepository {

   suspend fun getOrderDetailInfo(orderId:String):OrderDetailInfoVo

}