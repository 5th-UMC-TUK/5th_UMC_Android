package com.lacuna.chapter1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lacuna.chapter1.databinding.FragmentAlbumDetailBinding

class AlbumDetailFragment : Fragment() {

    lateinit var binding: FragmentAlbumDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

}