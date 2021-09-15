package com.hjhan.shoppingproject.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LikeItem(
    @PrimaryKey(autoGenerate = true) val likeId: Long = 0,
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "isNew") val isNew: Boolean,
    @ColumnInfo(name = "sellCount") val sellCount: Long,
    @ColumnInfo(name = "actualPrice") val actualPrice: Long,
    @ColumnInfo(name = "price") val price: Long
)