package com.hjhan.shoppingproject.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hjhan.shoppingproject.databinding.ItemGoodsBinding
import com.hjhan.shoppingproject.remote.GoodItem

class GoodsListAdapter(private var listener: OnClickItemListener) :
    ListAdapter<GoodItem, GoodsListAdapter.GoodsViewHolder>(itemDiff) {

    interface OnClickItemListener {
        fun onAddLike(selectedItem: GoodItem)
        fun onDeleteLike(selectedItem: GoodItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {

        val binding =
            ItemGoodsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GoodsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        currentList[position]?.let { holder.bind(it) }
    }

    override fun getItemId(position: Int): Long {
        return currentList[position].id
    }

    inner class GoodsViewHolder(itemView: ItemGoodsBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(data: GoodItem) {
            Log.e("TAG", "bind: ${data.id}  >>  ${data.isLikedItem}" )
            binding.goods = data
            binding.goodsLikeButton.isSelected = data.isLikedItem
            binding.goodsLikeButton.setOnClickListener {
                if (data.isLikedItem) {
                    listener.onDeleteLike(data)
                } else {
                    listener.onAddLike(data)
                }
            }
        }
    }
}

private val itemDiff = object : DiffUtil.ItemCallback<GoodItem>() {
    override fun areItemsTheSame(oldItem: GoodItem, newItem: GoodItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: GoodItem,
        newItem: GoodItem
    ): Boolean {
        return (oldItem == newItem) && (oldItem.isLikedItem == newItem.isLikedItem)
    }
}