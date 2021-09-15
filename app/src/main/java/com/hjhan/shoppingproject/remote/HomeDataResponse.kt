package com.hjhan.shoppingproject.remote

import com.google.gson.annotations.SerializedName

data class HomeDataResponse(
    val banners: List<BannerItem>,
    val goods: List<GoodItem>
)

data class BannerItem(
    val id: Long,
    val image: String
): HomeListItem()

data class GoodItem(
    val id: Long,
    val name: String,
    val image: String,

    @SerializedName("is_new")
    val isNew: Boolean,

    @SerializedName("sell_count")
    val sellCount: Long,

    @SerializedName("actual_price")
    val actualPrice: Long,

    val price: Long,

    var isLikedItem: Boolean

): HomeListItem() {
    val goodsDiscountRatio: String
        get() {
            return "${(price * 100 / actualPrice).toInt()}%"
        }

    val isDisCountNow: Boolean
        get() {
            return price == actualPrice
        }

    val goodsPriceStr: String
        get() {
            return String.format("%,d", price)
        }

    val sellCountStr: String
        get() {
            return String.format("%,d개 구매중", sellCount)
        }

    val isShowNewBadge: Boolean
        get() = isNew
}

sealed class HomeListItem