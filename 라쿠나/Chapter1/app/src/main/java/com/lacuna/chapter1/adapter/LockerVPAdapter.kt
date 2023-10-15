package com.lacuna.chapter1.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lacuna.chapter1.locker.MusicFileFragment
import com.lacuna.chapter1.locker.SavedSongFragment

class LockerVPAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> SavedSongFragment()
            else -> MusicFileFragment()
        }
    }
}