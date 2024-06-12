package com.example.idollapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAddressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userAddressEntity: UserAddressEntity): Long

    @Query("SELECT * FROM user_address WHERE id = :id")
    suspend fun findAddress(id: Int): UserAddressEntity

    @Delete
    suspend fun delete(userAddressEntity: UserAddressEntity)

    @Query("DELETE FROM user_address WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM user_address")
    fun findAddressList(): Flow<List<UserAddressEntity>>

    @Query("SELECT * FROM user_address WHERE userId = :userId")
    fun findAddressListByUserId(userId:String): Flow<List<UserAddressEntity>>

}
