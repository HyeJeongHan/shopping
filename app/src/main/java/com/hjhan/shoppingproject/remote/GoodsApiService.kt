package com.hjhan.shoppingproject.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GoodsApiService {
    @GET("home")
    fun getHomeData(): Single<HomeDataResponse>

    @GET("home/goods")
    fun getGoods(
        @Query("lastId") lastId: Long
    ): Single<HomeDataResponse>
}