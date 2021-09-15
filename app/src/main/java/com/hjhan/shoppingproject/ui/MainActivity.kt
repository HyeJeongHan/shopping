package com.hjhan.shoppingproject.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hjhan.shoppingproject.R
import com.hjhan.shoppingproject.databinding.ActivityMainBinding
import com.hjhan.shoppingproject.ui.home.HomeFragment
import com.hjhan.shoppingproject.ui.like.LikeFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val homeFragment by lazy {
        HomeFragment()
    }
    private val likeFragment by lazy {
        LikeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.home_frame_layout, likeFragment, "2")
            .hide(likeFragment).commit()
        supportFragmentManager.beginTransaction()
            .add(R.id.home_frame_layout, homeFragment, "1")
            .hide(homeFragment).commit()

        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .hide(likeFragment)
                        .show(homeFragment)
                        .commit()
                }
                R.id.nav_like -> {
                    supportFragmentManager.beginTransaction()
                        .hide(homeFragment)
                        .show(likeFragment)
                        .commit()
                }
            }
            true
        }
    }

}