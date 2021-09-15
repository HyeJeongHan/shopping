package com.hjhan.shoppingproject.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hjhan.shoppingproject.local.entity.LikeItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface GoodsLikeDao {
    @Query("SELECT * FROM LikeItem")
    fun getLikeGoods(): Single<List<LikeItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addLikeGoods(selectedItem: LikeItem): Completable

    @Query("DELETE FROM LikeItem WHERE id = :likeId")
    fun deleteLikeGoods(likeId: Long): Completable

}