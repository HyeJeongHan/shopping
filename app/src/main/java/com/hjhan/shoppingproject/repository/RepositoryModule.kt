package com.hjhan.shoppingproject.repository

import android.content.Context
import com.hjhan.shoppingproject.local.GoodsLikeDao
import com.hjhan.shoppingproject.local.LocalDatabase
import com.hjhan.shoppingproject.remote.RemoteClient
import com.hjhan.shoppingproject.remote.GoodsApiService
import com.hjhan.shoppingproject.datasource.GoodsLocalDataSource
import com.hjhan.shoppingproject.datasource.GoodsLocalDataSourceImpl
import com.hjhan.shoppingproject.datasource.GoodsRemoteDataSource
import com.hjhan.shoppingproject.datasource.GoodsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideGoodsRepository(repositoryImpl: GoodsRepositoryImpl): GoodsRepository

}

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideGoodsRemoteDataSource(dataSourceImpl: GoodsRemoteDataSourceImpl): GoodsRemoteDataSource

    @Binds
    @Singleton
    abstract fun provideGoodsLocalDataSource(dataSourceImpl: GoodsLocalDataSourceImpl): GoodsLocalDataSource

}

@Module
@InstallIn(ApplicationComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideShopService(): GoodsApiService {
        return RemoteClient.getApi()
    }

    @Singleton
    @Provides
    fun provideGoodsLikeService(@ApplicationContext context: Context): GoodsLikeDao {
        return LocalDatabase.getDatabase(context).goodsDao()
    }
}