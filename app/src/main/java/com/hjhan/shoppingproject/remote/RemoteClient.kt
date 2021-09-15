package com.hjhan.shoppingproject.remote

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ActivityComponent::class)
object RemoteClient {

    const val BASE_URL = "http://d2bab9i9pr8lds.cloudfront.net/api/"

    fun getApi(): GoodsApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GoodsApiService::class.java)

    private fun provideOkHttpClient(): OkHttpClient {
        val b = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        b.addInterceptor(loggingInterceptor)
        return b.build()
    }


}