package com.hgh.flo_clone.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hgh.flo_clone.HomeBannerFragment
import com.hgh.flo_clone.databinding.FragmentHomeBannerBinding

class HomeBannerRVAdapter(fragment: Fragment, val fragmentList: List<HomeBannerFragment>):FragmentStateAdapter(fragment)  {
    override fun getItemCount() = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}