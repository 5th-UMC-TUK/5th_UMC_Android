package com.hgh.flo_clone.locker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.hgh.flo_clone.MainActivity
import com.hgh.flo_clone.adapter.SaveAlbumRVAdapter
import com.hgh.flo_clone.databinding.FragmentSongFileBinding
import com.hgh.flo_clone.server.repository.ApiRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SongFileFragment : Fragment(){
    lateinit var binding: FragmentSongFileBinding
    val apiService by inject<ApiRepository>()
    val adapter by lazy {
        SaveAlbumRVAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSongFileBinding.inflate(inflater,container,false)
        .also {binding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveSongRV.adapter = adapter

        lifecycleScope.launch {
            adapter.setList(
                apiService.getAlbumList()?.filter {
                    it.albumIdx in (requireActivity() as MainActivity).likeAlbum
                } ?: listOf()
            )
        }

    }
}