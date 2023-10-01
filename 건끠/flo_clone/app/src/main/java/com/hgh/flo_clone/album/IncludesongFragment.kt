package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hgh.flo_clone.adapter.IncludeSongRVAdapter
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.databinding.FragmentIncludesongBinding

class IncludesongFragment: Fragment() {
    lateinit var binding: FragmentIncludesongBinding

    private val includeSongAdapter by lazy { IncludeSongRVAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentIncludesongBinding.inflate(inflater,container,false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.includeSongRV.adapter = includeSongAdapter
        includeSongAdapter.setList(
            (requireParentFragment() as AlbumFragment).args.argAlbum.musicList
        )
        binding.includeSwitch.setOnClickListener {
        }
    }

}