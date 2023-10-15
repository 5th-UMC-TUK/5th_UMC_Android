package com.lacuna.chapter1.locker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.lacuna.chapter1.adapter.LockerVPAdapter
import com.lacuna.chapter1.databinding.FragmentLockerBinding

class LockerFragment : Fragment() {
    lateinit var binding: FragmentLockerBinding
    private val lockerTab = arrayListOf("저장한 곡", "음악파일")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp) {
                tab, position ->
            tab.text = lockerTab[position]
        }.attach()
        return binding.root
    }

}