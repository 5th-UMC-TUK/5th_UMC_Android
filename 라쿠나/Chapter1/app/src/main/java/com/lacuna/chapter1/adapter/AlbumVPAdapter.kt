package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacuna.chapter1.view.album.AlbumDetailFragment
import com.lacuna.chapter1.view.album.AlbumIncludedSongsFragment
import com.lacuna.chapter1.view.album.AlbumVideoFragment

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3 // 수록곡, 상세정보, 영상 -> 3개의 프래그먼트를 만들거임

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> AlbumIncludedSongsFragment()
            1 -> AlbumDetailFragment()
            else -> AlbumVideoFragment()
        }
    }

}