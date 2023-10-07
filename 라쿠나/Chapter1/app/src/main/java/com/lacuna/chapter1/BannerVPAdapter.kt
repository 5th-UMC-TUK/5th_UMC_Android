package com.lacuna.chapter1

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragmentlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position] // getItemCount의 값이 4라면 0,1,2,3
    }

    fun addFragment(fragment: Fragment) {
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) // 리스트 안에 새로운 값이 추가되면 뷰 페이저에게 알림

    }
}