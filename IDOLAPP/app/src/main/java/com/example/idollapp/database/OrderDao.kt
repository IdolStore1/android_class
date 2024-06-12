package com.example.idollapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.idollapp.order.OrderDetailInfoVo
import com.example.idollapp.order.OrderListItem

@Dao
interface OrderDao {

    @Insert
    suspend fun createOrder(orderEntity: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = OrderItem::class)
    suspend fun insertOrderItems(orderItems: List<OrderItem>)

    @Query("SELECT * FROM OrderEntity WHERE id = :id ")
    suspend fun findOrderById(id: String): OrderEntity?

    @Query("SELECT * FROM OrderItem WHERE orderId = :id")
    suspend fun findOrderItemsById(id: String): List<OrderItem>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(copy: OrderEntity)

    @Query("SELECT * FROM OrderEntity WHERE status = :status AND userId = :userId ")
    suspend fun getOrders(userId: String, status: Int): List<OrderEntity>

//    @Transaction
//    @Query(
//        """
//        SELECT * FROM OrderEntity
//        WHERE OrderEntity.id IN (
//            SELECT orderId FROM OrderItem
//            WHERE orderId IN (
//                SELECT id FROM OrderEntity
//                WHERE userId = :userId AND status = :status
//            )
//        )
//    """
//    )
//    fun getOrdersWithItemsForUser(userId: String, status: Int): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM OrderEntity  WHERE userId = :userId AND status = :status ")
    suspend fun getOrdersWithItemsForUser(userId: String, status: Int): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM OrderEntity  WHERE userId = :userId ")
    suspend fun getOrdersWithItemsForUser(userId: String ): List<OrderWithItems>

    @Transaction
    @Query("SELECT * FROM OrderEntity  WHERE id = :orderId ")
    suspend fun getOrderDetailInfo(orderId: String): OrderWithItemsAndAddress

}