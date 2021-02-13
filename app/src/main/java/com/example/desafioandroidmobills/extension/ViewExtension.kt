package com.example.desafioandroidmobills.extension

import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

fun TabLayout.setupWithViewPagerV2(viewPager: ViewPager2, labels: List<String>) {
    TabLayoutMediator(this, viewPager,
        TabLayoutMediator.TabConfigurationStrategy { _, _ ->
        }).attach()
}