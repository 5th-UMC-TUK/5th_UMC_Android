package com.hgh.flo_clone.locker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hgh.flo_clone.LockerFragment
import com.hgh.flo_clone.album.SongDetailFragment

class TabLockerAdapter(fragmentActivity: LockerFragment): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> SaveSongFragment()
            1 -> SongDetailFragment()
            2 -> SongLikeFragment()
            else -> SaveSongFragment()
        }
    }
}