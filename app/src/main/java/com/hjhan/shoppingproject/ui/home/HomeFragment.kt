package com.hjhan.shoppingproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.hjhan.shoppingproject.R
import com.hjhan.shoppingproject.databinding.FragmentHomeBinding
import com.hjhan.shoppingproject.remote.GoodItem
import com.hjhan.shoppingproject.ui.GoodsListAdapter
import com.hjhan.shoppingproject.ui.MainViewModel
import com.hjhan.shoppingproject.ui.decoration.LikeSpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(), GoodsListAdapter.OnClickItemListener {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val goodsAdapter by lazy { GoodsListAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        init()

        return binding.root
    }

    private fun init() {
        initGoodsListObserver()
        showNetworkToastObserver()
        initRefreshLayout()
        initGoodsAdapter()
        viewModel.getHomeData()
    }

    private fun initRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getHomeData()
        }
    }

    private fun initGoodsAdapter() {
        with(binding.goodsRecyclerview) {
            itemAnimator = null
            layoutManager = GridLayoutManager(requireContext(), 2)
            addItemDecoration(LikeSpacesItemDecoration(2, 20))
            adapter = goodsAdapter
            setOnScrollChangeListener { _, _, _, _, _ ->
                if (!canScrollVertically(1)) {
                    viewModel.getGoods()
                }
            }
        }
    }

    private fun initGoodsListObserver() {
        viewModel.goodsList.observe(viewLifecycleOwner, {
            binding.swipeRefreshLayout.isRefreshing = false
            goodsAdapter.submitList(it.map { item -> item.copy() })
        })
    }

    private fun showNetworkToastObserver() {
        viewModel.showNetworkError.observe(viewLifecycleOwner, {
            Toast.makeText(requireContext(), "잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
        })
    }

    override fun onAddLike(selectedItem: GoodItem) {
        viewModel.addLikeGoods(selectedItem)
    }

    override fun onDeleteLike(selectedItem: GoodItem) {
        viewModel.deleteLikeGoods(selectedItem)
    }
}