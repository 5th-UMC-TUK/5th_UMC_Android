package com.hgh.flo_clone.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hgh.flo_clone.adapter.IncludeSongRVAdapter
import com.hgh.flo_clone.adapter.VideoRVAdapter
import com.hgh.flo_clone.databinding.FragmentIncludesongBinding
import kotlinx.coroutines.launch

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
//        includeSongAdapter.setList(
//            (requireParentFragment() as AlbumFragment).args.argAlbum.musicList
//        )
        lifecycleScope.launch {
            includeSongAdapter.setList(
                (requireParentFragment() as AlbumFragment).apiService.getAlbumInList((requireParentFragment() as AlbumFragment).args.argAlbum)?: listOf()
            )
        }
        binding.includeSwitch.setOnClickListener {
        }
    }

}