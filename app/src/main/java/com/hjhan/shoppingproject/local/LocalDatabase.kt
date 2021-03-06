package com.hjhan.shoppingproject.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hjhan.shoppingproject.local.entity.LikeItem

@Database(entities = [LikeItem::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun goodsDao(): GoodsLikeDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "goods_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}