package com.example.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chapter1.view.IncludedSongFragment
import com.example.chapter1.view.MoreInfoFragment
import com.example.chapter1.view.VideoFragment

class AlbumViewpagerAdapter(fragment: Fragment, albumIdx: Int) : FragmentStateAdapter(fragment) {
    private val fragments =
        listOf(IncludedSongFragment(albumIdx), MoreInfoFragment(), VideoFragment())

    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}