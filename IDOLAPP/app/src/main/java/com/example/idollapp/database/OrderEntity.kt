package com.example.idollapp.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: String,
    val orderSn: String,
    val title: String,
    val remark: String,
    val status: Int,
    val addressId: Int,
    val paymentTime:Long,
)

data class OrderWithItems(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>
)
data class OrderWithItemsAndAddress(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val items: List<OrderItem>,
    @Relation(
        parentColumn = "addressId",
        entityColumn = "id"
    )
    val address: UserAddressEntity?
)