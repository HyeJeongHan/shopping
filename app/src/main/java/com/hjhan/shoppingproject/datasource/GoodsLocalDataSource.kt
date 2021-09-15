package com.hjhan.shoppingproject.datasource

import com.hjhan.shoppingproject.local.GoodsLikeDao
import com.hjhan.shoppingproject.local.entity.LikeItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class GoodsLocalDataSourceImpl @Inject constructor(
    private val likeGoodsDao: GoodsLikeDao
) : GoodsLocalDataSource {

    override fun getLikeGoods(): Single<List<LikeItem>> {
        return likeGoodsDao.getLikeGoods()
    }

    override fun addLikeGoods(selectedItem: LikeItem): Completable {
        return likeGoodsDao.addLikeGoods(selectedItem)
    }

    override fun deleteLikeGoods(likeId: Long): Completable {
        return likeGoodsDao.deleteLikeGoods(likeId)
    }
}

interface GoodsLocalDataSource {
    fun getLikeGoods(): Single<List<LikeItem>>
    fun addLikeGoods(selectedItem: LikeItem): Completable
    fun deleteLikeGoods(likeId: Long): Completable
}