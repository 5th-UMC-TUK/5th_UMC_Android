package com.lacuna.chapter1.view.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lacuna.chapter1.databinding.FragmentMusicFileBinding

class LockerMusicFileFragment: Fragment() {

    lateinit var binding: FragmentMusicFileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMusicFileBinding.inflate(inflater, container, false)

        return binding.root
    }
}