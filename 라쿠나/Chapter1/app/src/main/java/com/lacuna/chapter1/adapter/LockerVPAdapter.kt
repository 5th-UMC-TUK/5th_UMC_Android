package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacuna.chapter1.locker.LockerMusicFileFragment
import com.lacuna.chapter1.locker.LockerSavedAlbumFragment
import com.lacuna.chapter1.locker.LockerSavedSongFragment

class LockerVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LockerSavedSongFragment()
            1 -> LockerMusicFileFragment()
            else -> LockerSavedAlbumFragment()
        }
    }
}