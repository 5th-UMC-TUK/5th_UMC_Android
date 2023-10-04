package com.example.chapter1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chapter1.adapter.LockViewpagerAdapter
import com.example.chapter1.databinding.FragmentLockBinding
import com.google.android.material.tabs.TabLayoutMediator


class LockFragment : Fragment() {
    private lateinit var binding: FragmentLockBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lockViewpager.adapter = LockViewpagerAdapter(this@LockFragment)
        val tabs = arrayOf("저장한 곡", "음악파일")
        TabLayoutMediator(binding.lockTab, binding.lockViewpager) { tab, position ->
            tab.text = tabs[position]
        }.attach()
    }


}