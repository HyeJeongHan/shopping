package com.hjhan.shoppingproject.ui

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjhan.shoppingproject.remote.GoodItem
import com.hjhan.shoppingproject.remote.toGoodsItemList
import com.hjhan.shoppingproject.remote.toLikeItem
import com.hjhan.shoppingproject.repository.GoodsRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(
    private val goodsRepository: GoodsRepositoryImpl
) : ViewModel() {

    private val _goodsList = MutableLiveData<List<GoodItem>>()
    val goodsList: LiveData<List<GoodItem>>
        get() = _goodsList

    private val _likeGoodsList = MutableLiveData<List<GoodItem>>()
    val likeGoodsList: LiveData<List<GoodItem>>
        get() = _likeGoodsList

    private val _showNetworkError = MutableLiveData<Unit>()
    val showNetworkError: LiveData<Unit>
        get() = _showNetworkError

    private val goodsArrayList = arrayListOf<GoodItem>()

    private var lastGoodsId: Long? = null

    private val compositeDisposable = CompositeDisposable()

    fun getHomeData() {
        goodsRepository.getHomeData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.d("TAG", "getHomeData: $response")
                goodsArrayList.clear()
                val goodsResponseList = response.goods
                lastGoodsId = if (goodsResponseList.isEmpty()) {
                    null
                } else {
                    goodsResponseList[goodsResponseList.size - 1].id
                }
                goodsArrayList.addAll(goodsResponseList)
                _goodsList.postValue(goodsArrayList)

            }, { error ->
                error.printStackTrace()
                _showNetworkError.value = Unit
            }).also {
                compositeDisposable.add(it)
            }
    }

    fun getGoods() {
        Log.d("TAG", "lastGoodsId.value?: ${lastGoodsId}")
        lastGoodsId?.let {
            goodsRepository.getGoods(it)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ response ->
                    Log.d("TAG", "addGoods: $response")
                    val goodsResponseList = response.goods
                    lastGoodsId = if (goodsResponseList.isEmpty()) {
                        null
                    } else {
                        goodsResponseList[goodsResponseList.size - 1].id
                    }
                    goodsArrayList.addAll(goodsResponseList)
                    _goodsList.postValue(goodsArrayList)

                }, { error ->
                    error.printStackTrace()
                    _showNetworkError.value = Unit
                }).also { disposable ->
                    compositeDisposable.add(disposable)
                }
        } ?: run {
            lastGoodsId = null
        }
    }

    fun getLikeGoods() {
        goodsRepository.getLikeGoods()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                Log.d("TAG", "getLikeGoods: $response")
                _likeGoodsList.postValue(response.toGoodsItemList())

            }, { error ->
                error.printStackTrace()
                _showNetworkError.value = Unit
            }).also { disposable ->
                compositeDisposable.add(disposable)
            }
    }


    fun addLikeGoods(selectedItem: GoodItem) {
        Log.d("TAG", "addLikeGoods: ${selectedItem}")
        goodsRepository.addLikeGoods(selectedItem.toLikeItem())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getLikeGoods()
                updateHomeGoodsListForAddLike(selectedItem.id)

            }, { error ->
                error.printStackTrace()
                _showNetworkError.value = Unit
            }).also { disposable ->
                compositeDisposable.add(disposable)
            }
    }

    fun deleteLikeGoods(selectedItem: GoodItem) {
        Log.d("TAG", "deleteLikeGoods: ${selectedItem}")
        goodsRepository.deleteLikeGoods(selectedItem.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                getLikeGoods()
                updateHomeGoodsListForDeleteLike(selectedItem.id)

            }, { error ->
                error.printStackTrace()
                _showNetworkError.value = Unit
            }).also { disposable ->
                compositeDisposable.add(disposable)
            }
    }

    private fun updateHomeGoodsListForAddLike(id: Long) {
        Log.d("TAG", "updateHomeGoodsListForAddLike: ${id}")
        goodsArrayList.find { id == it.id }?.isLikedItem = true
        _goodsList.postValue(goodsArrayList)
    }

    private fun updateHomeGoodsListForDeleteLike(id: Long) {
        Log.d("TAG", "updateHomeGoodsListForDeleteLike: ${id}")
        goodsArrayList.find { id == it.id }?.isLikedItem = false
        _goodsList.postValue(goodsArrayList)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}