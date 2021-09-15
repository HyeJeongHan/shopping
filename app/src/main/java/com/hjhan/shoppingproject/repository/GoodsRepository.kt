package com.hjhan.shoppingproject.repository

import com.hjhan.shoppingproject.datasource.GoodsLocalDataSource
import com.hjhan.shoppingproject.datasource.GoodsRemoteDataSource
import com.hjhan.shoppingproject.local.entity.LikeItem
import com.hjhan.shoppingproject.remote.HomeDataResponse
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class GoodsRepositoryImpl @Inject constructor(
    private val remoteDataSource: GoodsRemoteDataSource,
    private val localDataSource: GoodsLocalDataSource
) : GoodsRepository {

    override fun getHomeData(): Single<HomeDataResponse> {
        return Single.zip(localDataSource.getLikeGoods(), remoteDataSource.getHomeData()) { likeItem, homeData ->
            if (likeItem.isNotEmpty()) {
                likeItem.forEach { like ->
                    homeData.goods.find { like.id == it.id }?.isLikedItem = true
                }
            }
            homeData
        }
    }

    override fun getGoods(lastId: Long): Single<HomeDataResponse> {
        return Single.zip(localDataSource.getLikeGoods(), remoteDataSource.getGoods(lastId)) { likeItem, homeData ->
            if (likeItem.isNotEmpty()) {
                likeItem.forEach { like ->
                    homeData.goods.find { like.id == it.id }?.isLikedItem = true
                }
            }
            homeData
        }
    }

    override fun getLikeGoods(): Single<List<LikeItem>> {
        return localDataSource.getLikeGoods()
    }

    override fun addLikeGoods(selectedItem: LikeItem): Completable {
        return localDataSource.addLikeGoods(selectedItem)
    }

    override fun deleteLikeGoods(likeId: Long): Completable {
        return localDataSource.deleteLikeGoods(likeId)
    }
}

interface GoodsRepository {
    fun getHomeData(): Single<HomeDataResponse>
    fun getGoods(lastId: Long): Single<HomeDataResponse>
    fun getLikeGoods(): Single<List<LikeItem>>
    fun addLikeGoods(selectedItem: LikeItem): Completable
    fun deleteLikeGoods(likeId: Long): Completable

}