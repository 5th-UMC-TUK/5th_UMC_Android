package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.databinding.FragmentSongDetailBinding
import com.hgh.flo_clone.databinding.FragmentSongVideoBinding

class SongVideoFragment: Fragment() {
    lateinit var binding: FragmentSongVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSongVideoBinding.inflate(inflater,container,false)
        .also { binding = it }.root

}