package com.example.idollapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderItemDao {

    @Insert
    fun insert(orderItem: OrderItem): Long

    @Insert
    fun insert(orderItems: List<OrderItem>): List<Long>

    @Query("SELECT * FROM OrderItem WHERE orderId = :orderId")
    fun queryByOrderId(orderId: Int): List<OrderItem>

}