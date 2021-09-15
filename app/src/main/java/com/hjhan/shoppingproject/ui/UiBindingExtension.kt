package com.hjhan.shoppingproject.ui

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.hjhan.shoppingproject.remote.GoodItem

@BindingAdapter("app:visibleOrGone")
fun View.viewVisibleOrGone(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bind:imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) {
    imageUrl?.let {
        Glide.with(this.context)
            .load(it)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}

@BindingAdapter("setGoodItemList")
fun RecyclerView.setGoodItemList(list: List<GoodItem>?) {
    list?.let {
        (adapter as GoodsListAdapter).submitList(it)
    }
}

@BindingAdapter("setGoodItemSelected")
fun View.setGoodItemSelected(isSelect: Boolean) {
    isSelected = isSelect
}