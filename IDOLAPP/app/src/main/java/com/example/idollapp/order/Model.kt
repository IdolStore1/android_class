package com.example.idollapp.order

import com.example.idollapp.database.OrderItem
import com.example.idollapp.goods.CartItem
import com.example.idollapp.goods.Goods
import kotlin.random.Random


sealed interface IOrderStatus {
    fun title(): String
    fun value():Int

    data object All : IOrderStatus {
        override fun title(): String {
            return "全部"
        }

        override fun value(): Int {
            return 0
        }
    }

    data object WaitPay : IOrderStatus {
        override fun title(): String {
            return "待付款"
        }

        override fun value(): Int {
            return 1
        }
    }

    data object WaitSend : IOrderStatus {
        override fun title(): String {
            return "待发货"
        }

        override fun value(): Int {
            return 2
        }
    }

    data object WaitReceive : IOrderStatus {
        override fun title(): String {
            return "待收货"
        }

        override fun value(): Int {
            return 3
        }
    }

    data object WaitComment : IOrderStatus {
        override fun title(): String {
            return "待评论"
        }

        override fun value(): Int {
            return 4
        }
    }

    companion object {
        fun covert(status: Int): IOrderStatus {
            return when (status) {
                1 -> IOrderStatus.WaitPay
                2 -> IOrderStatus.WaitSend
                3 -> IOrderStatus.WaitReceive
                4 -> IOrderStatus.WaitComment
                else -> IOrderStatus.All
            }
        }
    }
}

fun randomStatus(): IOrderStatus {
    return when (Random(System.currentTimeMillis()).nextInt(4)) {
        1 -> IOrderStatus.WaitPay
        2 -> IOrderStatus.WaitSend
        3 -> IOrderStatus.WaitReceive
        4 -> IOrderStatus.WaitComment
        else -> IOrderStatus.All
    }
}

fun CartItem.toOrderItem(orderId: Int):OrderItem{
    return OrderItem(
        orderId = orderId,
        quantity = quantity,
        price = item.price,
        productId = item.id ,
        productTitle = item.name,
        productImage = item.pic,
    )
}

fun OrderItem.toGoods(): Goods {
    return Goods(
        id = productId,
        name = productTitle,
        price = price,
        pic = productImage,
        sold = 0
    )
}

data class OrderListItem(
    val id: String,
    val sn: String,
    val title: String,
    val imageUrl: String,
    val price: String,
    val freight: String, // 运费
    val remark: String,
    val status: IOrderStatus,
)


data class OrderDetailInfoVo(
    val orderId: String,
    val title: String,
    val payAmount: Double,
    val quantity: Int,
    val receiverDetailAddress: String,
    val receiverName: String,
    val receiverPhone: String,
    val orderSn: String,
    val paymentTime: String?,
    val status: IOrderStatus,
    val goodsList: List<Goods>,
)