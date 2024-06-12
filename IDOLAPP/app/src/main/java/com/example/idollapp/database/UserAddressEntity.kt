package com.example.idollapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_address")
data class UserAddressEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val userId: String,
    val name: String, val phone: String, val address: String
)
