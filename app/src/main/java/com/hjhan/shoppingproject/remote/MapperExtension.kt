package com.hjhan.shoppingproject.remote

import com.hjhan.shoppingproject.local.entity.LikeItem


fun List<LikeItem>?.toGoodsItemList(): List<GoodItem> {
    if (this == null) return emptyList()
    return map { item ->
        GoodItem(
            id = item.id,
            image = item.image,
            name = item.name,
            isNew = item.isNew,
            sellCount = item.sellCount,
            actualPrice = item.actualPrice,
            price = item.price,
            isLikedItem = true
        )
    }
}

fun GoodItem.toLikeItem(): LikeItem {
    return LikeItem(
        id = id,
        image = image,
        name = name,
        isNew = isNew,
        sellCount = sellCount,
        actualPrice = actualPrice,
        price = price
    )
}