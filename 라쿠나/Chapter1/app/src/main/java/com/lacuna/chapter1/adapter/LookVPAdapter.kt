package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacuna.chapter1.LookChartFragment


class LookVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2 // chart만 표현

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LookChartFragment()
            else -> LookChartFragment()
        }
    }

}