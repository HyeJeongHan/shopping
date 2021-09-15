package com.hjhan.shoppingproject.ui.like

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hjhan.shoppingproject.R
import com.hjhan.shoppingproject.databinding.FragmentLikeBinding
import com.hjhan.shoppingproject.remote.GoodItem
import com.hjhan.shoppingproject.ui.GoodsListAdapter
import com.hjhan.shoppingproject.ui.MainViewModel
import com.hjhan.shoppingproject.ui.decoration.LikeSpacesItemDecoration

class LikeFragment : Fragment(), GoodsListAdapter.OnClickItemListener {

    private lateinit var binding: FragmentLikeBinding

    private val viewModel by activityViewModels<MainViewModel>()

    private val likeGoodsAdapter by lazy {
        GoodsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        return binding.root
    }

    private fun init() {

        initRefreshLayout()
        initLikeGoodsAdapter()
        initLikeGoodsListObserver()

        viewModel.getLikeGoods()
    }

    private fun initRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getLikeGoods()
        }
    }

    private fun initLikeGoodsAdapter() {
        likeGoodsAdapter.setHasStableIds(true)
        with(binding.likeRecyclerview) {
            itemAnimator = null
            adapter = likeGoodsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(LikeSpacesItemDecoration(2, 20))
        }
    }
    private fun initLikeGoodsListObserver() {
        viewModel.likeGoodsList.observe(viewLifecycleOwner, {
            likeGoodsAdapter.submitList(it)
            likeGoodsAdapter.notifyDataSetChanged()
            binding.swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun onAddLike(selectedItem: GoodItem) {

    }

    override fun onDeleteLike(selectedItem: GoodItem) {
        viewModel.deleteLikeGoods(selectedItem)
    }
}