package com.example.idollapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val quantity: Int,
    val price: Float,
    val productId: String,
    val productTitle: String,
    val productImage: String,
)
