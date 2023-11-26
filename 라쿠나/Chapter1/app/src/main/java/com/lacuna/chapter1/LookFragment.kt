package com.lacuna.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import com.lacuna.chapter1.adapter.AlbumVPAdapter
import com.lacuna.chapter1.adapter.LookVPAdapter
import com.lacuna.chapter1.databinding.FragmentLookBinding


class LookFragment : Fragment() {
    lateinit var binding: FragmentLookBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)
        val lookAdapter = LookVPAdapter(this)
        binding.lookVp.adapter = lookAdapter

        return binding.root
    }
}