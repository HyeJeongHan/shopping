package com.hjhan.shoppingproject.datasource

import com.hjhan.shoppingproject.remote.GoodsApiService
import com.hjhan.shoppingproject.remote.HomeDataResponse
import io.reactivex.Single
import javax.inject.Inject

class GoodsRemoteDataSourceImpl @Inject constructor(
    private val goodsApi: GoodsApiService
) : GoodsRemoteDataSource {

    override fun getHomeData(): Single<HomeDataResponse> {
        return goodsApi.getHomeData()
    }

    override fun getGoods(lastId: Long): Single<HomeDataResponse> {
        return goodsApi.getGoods(lastId)
    }
}

interface GoodsRemoteDataSource {
    fun getHomeData(): Single<HomeDataResponse>
    fun getGoods(lastId: Long): Single<HomeDataResponse>
}
