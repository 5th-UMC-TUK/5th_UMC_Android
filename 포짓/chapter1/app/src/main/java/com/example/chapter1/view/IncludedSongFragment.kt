package com.example.chapter1.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chapter1.R
import com.example.chapter1.databinding.FragmentAlbumBinding
import com.example.chapter1.databinding.FragmentIncludedSongBinding


class IncludedSongFragment : Fragment() {
    private lateinit var binding: FragmentIncludedSongBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncludedSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}