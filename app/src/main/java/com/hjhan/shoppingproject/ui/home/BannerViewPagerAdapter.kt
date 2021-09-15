package com.hjhan.shoppingproject.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.hjhan.shoppingproject.databinding.ItemViewpagerBannerBinding
import com.hjhan.shoppingproject.remote.BannerItem

class BannerViewPagerAdapter :
    RecyclerView.Adapter<BannerViewPagerAdapter.BannerViewHolder>() {

    private var bannerList: List<BannerItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding =
            ItemViewpagerBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList)
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun setBannerList(banners: List<BannerItem>) {
        bannerList = banners
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(itemView: ItemViewpagerBannerBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        private val binding = itemView

        fun bind(data: List<BannerItem>) {
            val adapter = BannerAdapter()
            binding.viewPager.adapter = adapter
            binding.viewPager.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bannerPageText.text =
                        "${position + 1}/${data.size}"
                }
            })
            binding.bannerPageText.text = "${1}/${data.size}"
            adapter.setBannerList(data)
        }
    }
}