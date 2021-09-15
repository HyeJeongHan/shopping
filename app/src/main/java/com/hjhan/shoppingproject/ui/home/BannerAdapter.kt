package com.hjhan.shoppingproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hjhan.shoppingproject.databinding.ItemBannerBinding
import com.hjhan.shoppingproject.remote.BannerItem

class BannerAdapter :
    RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var bannerList: List<BannerItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int {
        return bannerList.size
    }

    fun setBannerList(banners: List<BannerItem>) {
        bannerList = banners
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(itemView: ItemBannerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(data: BannerItem) {
            binding.banner = data
        }
    }
}