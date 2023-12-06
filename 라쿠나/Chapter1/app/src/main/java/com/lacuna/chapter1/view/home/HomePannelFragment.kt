package com.lacuna.chapter1.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.databinding.FragmentHomePannelBinding

class HomePannelFragment(val titleText: String): Fragment() {
    lateinit var binding: FragmentHomePannelBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePannelBinding.inflate(inflater, container, false)
        binding.homePannelTitleTv.text = titleText
        return binding.root
    }
}