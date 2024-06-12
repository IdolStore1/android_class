package com.example.idollapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingCart(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: String,
    val productId: String,
    val productName: String,
    val productPrice: Float,
    val productImage: String,
    val productQuantity: Int,
//    val productSkuName: String,
)