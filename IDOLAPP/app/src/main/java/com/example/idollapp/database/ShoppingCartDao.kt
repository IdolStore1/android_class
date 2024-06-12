package com.example.idollapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingCartDao {


    // query by user id
    @Query("SELECT * FROM ShoppingCart WHERE userId = :userId")
    fun queryCartItems(userId: String): Flow<List<ShoppingCart>>

    // query by user id
    @Query("SELECT * FROM ShoppingCart WHERE userId = :userId")
    suspend fun getCartItems(userId: String): List<ShoppingCart>

    // insert items beans
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertCartItems(cartItems: List<ShoppingCart>): List<Long>

    @Update(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun updateCartItems(cartItems: List<ShoppingCart>)

    @Delete
    suspend fun deleteCartItems(cartItems: List<ShoppingCart>)

    @Query("DELETE FROM ShoppingCart WHERE userId = :id ")
    suspend fun deleteAll(id: String )

    @Query("DELETE FROM ShoppingCart WHERE userId = :id AND id in (:cartIds)")
    suspend fun deleteAll(id: String,cartIds :List<String>)

    @Query("SELECT * FROM ShoppingCart WHERE userId = :userId AND productId = :id")
    suspend fun findByGoodsIdUserId(id: String, userId: String): ShoppingCart?

}