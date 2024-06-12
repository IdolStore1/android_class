package com.example.idollapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.idollapp.collection.CollectionGoodsListItem
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsFavoriteDao {

    @Insert
    suspend fun insert(goodsFavorite: GoodsFavorite)

    @Delete
    suspend fun delete(goodsFavorite: GoodsFavorite)

    @Query("SELECT * FROM GoodsFavorite WHERE goodsId = :goodsId AND userId = :userId")
    suspend fun findByGoodsIdUserId(goodsId: String, userId: String): GoodsFavorite?

    @Query("SELECT * FROM GoodsFavorite WHERE userId = :userId")
    suspend fun getAllGoodsByUserId(userId: String): List<GoodsFavorite>

    @Delete
    suspend fun removeFromCollection(goods: GoodsFavorite)


}