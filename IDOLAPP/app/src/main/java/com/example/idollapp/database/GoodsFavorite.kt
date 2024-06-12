package com.example.idollapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoodsFavorite(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val goodsId: String,
    val userId: String,
    val createTime: Long,
    val title: String,
    val imageUrl: String,
    val price: Float,
)
