package com.example.chapter1.adapter


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chapter1.model.TitleAlbumModel
import com.example.chapter1.view.TitleFragment


class TitleAdapter(private val list: List<TitleAlbumModel>, baseFragment: Fragment) :
    FragmentStateAdapter(baseFragment) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        return TitleFragment(list[position])
    }


}