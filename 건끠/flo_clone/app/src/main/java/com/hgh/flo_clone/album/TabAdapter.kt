package com.hgh.flo_clone.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragmentActivity: AlbumFragment) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IncludesongFragment()
            1 -> SongDetailFragment()
            2 -> SongVideoFragment()
            else -> IncludesongFragment()
        }
    }
}