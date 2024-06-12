package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.ApiWrapper
import com.example.idollapp.http.bean.*

class OrderRepository {

    private fun createApi(): OrderApi {
        return ApiWrapper.create(OrderApi::class.java)
    }

    suspend fun createOrder(
        userId: String,
        spuId: Int,
        skuId: Int,
        count: Int,
        attrList: String
    ): Result<ApiResponse<CreateOrderReturnVo>> {

        val params = HashMap<String, Any>()
        params["userId"] = userId
        params["spuId"] = spuId
        params["skuId"] = skuId
        params["count"] = count
        params["attrList"] = attrList

        return kotlin.runCatching { createApi().createOrder(params) }
    }

    suspend fun payNow(
        userId: Int,
        totalAmount: Int,
        integrationAmount: Double,
        payAmount: Double,
        useIntegration: Int,
        spuId: Int,
        skuId: Int,
        skuQuantity: Int,
        skuAttrsVals: String,
        giftIntegration: Int,
        payType: Int,
        addressId: Int,
        note: String
    ): Result<ApiResponse<PaySuccessReturnVo>> {

        val params = HashMap<String, Any>()
        params["userId"] = userId
        params["totalAmount"] = totalAmount
        params["integrationAmount"] = integrationAmount
        params["payAmount"] = payAmount
        params["useIntegration"] = useIntegration
        params["spuId"] = spuId
        params["skuId"] = skuId
        params["skuQuantity"] = skuQuantity
        params["skuAttrsVals"] = skuAttrsVals
        params["giftIntegration"] = giftIntegration
        params["payType"] = payType
        params["addressId"] = addressId
        params["note"] = note

        return kotlin.runCatching { createApi().payNow(params) }
    }

    suspend fun distributeDelivery(
        orderId: Int,
        deliveryCompany: String,
        deliverySn: String
    ): Result<ApiResponse<Any>> {

        val params = HashMap<String, Any>()
        params["orderId"] = orderId
        params["deliveryCompany"] = deliveryCompany
        params["deliverySn"] = deliverySn

        return kotlin.runCatching { createApi().distributeDelivery(params) }
    }

    suspend fun selectUnpaidOrders(userId: String? = null): Result<ApiResponse<List<OrderInfoResponse>>> {
        return kotlin.runCatching { createApi().selectUnpaidOrders(userId) }
    }

    suspend fun selectUnshippedOrders(userId: String? = null): Result<ApiResponse<List<OrderInfoResponse>>> {
        return kotlin.runCatching { createApi().selectUnshippedOrders(userId) }
    }

    suspend fun selectShippedOrders(userId: String? = null): Result<ApiResponse<List<OrderInfoResponse>>> {
        return kotlin.runCatching { createApi().selectShippedOrders(userId) }
    }

    suspend fun selectReceivedOrders(userId: String? = null): Result<ApiResponse<List<OrderInfoResponse>>> {
        return kotlin.runCatching { createApi().selectReceivedOrders(userId) }
    }

    suspend fun selectAllOrders(userId: String? = null): Result<ApiResponse<List<OrderInfoResponse>>> {
        return kotlin.runCatching { createApi().selectAllOrders(userId) }
    }

    suspend fun getOrderDetailInfo(orderId: String): Result<ApiResponse<OrderDetailInfo>> {
        return kotlin.runCatching { createApi().getOrderDetailInfo(orderId) }
    }

    suspend fun createOrderByCart(
        userId: String,
        totalAmount: Int,
        payAmount: Double,
        integrationAmount: Double,
        useIntegration: Int,
        giftIntegration: Int,
        itemVos: List<CartItem>
    ): Result<ApiResponse<CreateOrderByCartReturnVo>> {

        val params = HashMap<String, Any>()
        params["userId"] = userId
        params["totalAmount"] = totalAmount
        params["payAmount"] = payAmount
        params["integrationAmount"] = integrationAmount
        params["useIntegration"] = useIntegration
        params["giftIntegration"] = giftIntegration
        params["itemVos"] = itemVos

        return kotlin.runCatching { createApi().createOrderByCart(params) }
    }

}
