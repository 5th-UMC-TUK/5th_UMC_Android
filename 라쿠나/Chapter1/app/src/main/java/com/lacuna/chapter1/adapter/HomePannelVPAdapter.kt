package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomePannelVPAdapter(fragemnt: Fragment): FragmentStateAdapter(fragemnt) {
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragemnt: Fragment) {
        fragmentlist.add(fragemnt)
        notifyItemInserted(fragmentlist.size-1) // 리스트 안에 새로운 값이 추가되면 뷰 페이저에게 알림

    }
}