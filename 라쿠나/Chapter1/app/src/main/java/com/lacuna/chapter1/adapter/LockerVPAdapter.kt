package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacuna.chapter1.locker.LockerMusicFileFragment
import com.lacuna.chapter1.locker.LockerSavedSongFragment

class LockerVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> LockerSavedSongFragment()
            else -> LockerMusicFileFragment()
        }
    }
}