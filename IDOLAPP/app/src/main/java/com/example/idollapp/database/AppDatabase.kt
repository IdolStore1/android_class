package com.example.idollapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [
        User::class,
        UserAddressEntity::class,
        OrderEntity::class,
        OrderItem::class,
        ShoppingCart::class,
        GoodsFavorite::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun goodsFavoriteDao(): GoodsFavoriteDao
    abstract fun shoppingCartDao(): ShoppingCartDao
    abstract fun userAddressDao(): UserAddressDao
    abstract fun orderDao(): OrderDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}
