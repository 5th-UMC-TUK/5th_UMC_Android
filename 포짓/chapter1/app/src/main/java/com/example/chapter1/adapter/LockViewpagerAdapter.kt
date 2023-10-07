package com.example.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chapter1.view.SavedSongFragment

class LockViewpagerAdapter (fragment: Fragment): FragmentStateAdapter(fragment) {
    private val fragments = listOf(SavedSongFragment(), SavedSongFragment())
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}