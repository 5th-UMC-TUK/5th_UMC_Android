package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.databinding.FragmentSongDetailBinding

class SongDetailFragment: Fragment() {
    lateinit var binding: FragmentSongDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSongDetailBinding.inflate(inflater,container,false)
        .also { binding = it }.root

}