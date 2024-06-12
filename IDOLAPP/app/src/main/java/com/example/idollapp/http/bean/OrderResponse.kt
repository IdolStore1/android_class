package com.example.idollapp.http.bean

data class CreateOrderReturnVo(
    val userId: Int,
    val totalAmount: Int,
    val integrationAmount: Double,
    val payAmount: Double,
    val receiverName: String,
    val receiverPhone: String,
    val province: String,
    val city: String,
    val region: String,
    val receiverDetailAddress: String,
    val useIntegration: Int,
    val spuId: Int,
    val skuId: Int,
    val skuName: String,
    val skuPic: String,
    val skuPrice: Int,
    val skuQuantity: Int,
    val skuAttrsVals: String,
    val giftIntegration: Int
)

data class PaySuccessReturnVo(
    val userId: Int,
    val orderSn: String,
    val createTime: String,
    val totalAmount: Int,
    val payAmount: Double,
    val freightAmount: Int,
    val integrationAmount: Double?,
    val payType: Int,
    val status: Int,
    val receiverName: String,
    val receiverPhone: String,
    val receiverPostCode: String,
    val receiverProvince: String,
    val receiverCity: String,
    val receiverRegion: String,
    val receiverDetailAddress: String,
    val note: String,
    val useIntegration: Int,
    val paymentTime: String,
    val spuId: Int,
    val idolId: Int,
    val skuId: Int,
    val skuName: String,
    val skuPic: String,
    val skuPrice: Int,
    val skuQuantity: Int,
    val skuAttrsVals: String,
    val giftIntegration: Int
)

data class DistributeDeliveryResponse(val empty: String = "")

data class OrderInfoResponse(
    val orderId: Int,
    val payAmount: Double,
   val orderSn: String,
)

data class OrderDetailInfo(
    val orderId: String,
    val skuId: String,
    val skuName: String,
    val skuPic: String,
    val skuPrice: Double,
    val skuQuantity: Int,
    val payAmount: Double,
    val skuAttrsVals: String,
    val receiverProvince: String,
    val receiverCity: String,
    val receiverRegion: String,
    val receiverDetailAddress: String,
    val receiverName: String,
    val receiverPhone: String,
    val orderSn: String,
    val payType: Int,
    val paymentTime: String
)

data class ItemReturnVo(
    val spuId: Int,
    val skuId: Int,
    val skuName: String,
    val skuPic: String,
    val skuPrice: Int,
    val skuQuantity: Int,
    val skuAttrsVals: String
)

data class CartItem(
    val spuId: Int,
    val skuId: Int,
    val skuName: String,
    val skuPic: String,
    val skuPrice: Int,
    val skuQuantity: Int,
    val skuAttrsVals: String
)

data class CreateOrderByCartReturnVo(
    val userId: Int,
    val totalAmount: Int,
    val integrationAmount: Double,
    val payAmount: Double,
    val useIntegration: Int,
    val giftIntegration: Int,
    val receiverName: String,
    val receiverPhone: String,
    val province: String,
    val city: String,
    val region: String,
    val receiverDetailAddress: String,
    val itemReturnVos: List<ItemReturnVo>
)






