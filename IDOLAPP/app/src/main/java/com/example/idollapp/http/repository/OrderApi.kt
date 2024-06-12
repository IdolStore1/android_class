package com.example.idollapp.http.repository

import com.example.idollapp.http.ApiResponse
import com.example.idollapp.http.HttpConstants
import com.example.idollapp.http.bean.CreateOrderByCartReturnVo
import com.example.idollapp.http.bean.CreateOrderReturnVo
import com.example.idollapp.http.bean.OrderDetailInfo
import com.example.idollapp.http.bean.OrderInfoResponse
import com.example.idollapp.http.bean.PaySuccessReturnVo
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OrderApi {

    @POST(HttpConstants.ORDER_BASE_URL + "order/order/createOrder")
    suspend fun createOrder(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<CreateOrderReturnVo>

    @POST(HttpConstants.ORDER_BASE_URL + "order/order/payNow")
    suspend fun payNow(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<PaySuccessReturnVo>

    @POST(HttpConstants.ORDER_BASE_URL + "order/order/distributeDelivery")
    suspend fun distributeDelivery(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<Any>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/selectUnpaidorders")
    suspend fun selectUnpaidOrders(@Query("userId") userId: String?): ApiResponse<List<OrderInfoResponse>>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/selectUnshippedorders")
    suspend fun selectUnshippedOrders(@Query("userId") userId: String?): ApiResponse<List<OrderInfoResponse>>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/selectShippedorders")
    suspend fun selectShippedOrders(@Query("userId") userId: String?): ApiResponse<List<OrderInfoResponse>>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/selectReceivedorders")
    suspend fun selectReceivedOrders(@Query("userId") userId: String?): ApiResponse<List<OrderInfoResponse>>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/selectAllorders")
    suspend fun selectAllOrders(@Query("userId") userId: String?): ApiResponse<List<OrderInfoResponse>>

    @GET(HttpConstants.ORDER_BASE_URL + "order/order/getOrderDetailInfo")
    suspend fun getOrderDetailInfo(@Query("orderId") orderId: String): ApiResponse<OrderDetailInfo>

    @POST(HttpConstants.ORDER_BASE_URL + "order/order/createOrderByCart")
    suspend fun createOrderByCart(@Body params: Map<String, @JvmSuppressWildcards Any>): ApiResponse<CreateOrderByCartReturnVo>
}
