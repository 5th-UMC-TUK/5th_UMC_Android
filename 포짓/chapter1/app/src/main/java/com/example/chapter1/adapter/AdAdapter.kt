package com.example.chapter1.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chapter1.databinding.AdListBinding
import com.example.chapter1.view.AdFragment

class AdAdapter(private val list: List<Int>, fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount() = list.size

    override fun createFragment(position: Int): Fragment {
        return AdFragment(list[position])
    }

}