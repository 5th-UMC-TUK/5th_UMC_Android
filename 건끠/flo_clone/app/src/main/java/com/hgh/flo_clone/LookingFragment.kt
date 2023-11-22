package com.hgh.flo_clone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayoutMediator
import com.hgh.flo_clone.adapter.LookingSongRVAdapter
import com.hgh.flo_clone.adapter.MusicRVAdapter
import com.hgh.flo_clone.album.TabAdapter
import com.hgh.flo_clone.databinding.FragmentHomeBinding
import com.hgh.flo_clone.databinding.FragmentLookingBinding
import com.hgh.flo_clone.locker.TabLockerAdapter
import com.hgh.flo_clone.server.repository.ApiRepository
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class LookingFragment : Fragment() {

    lateinit var binding: FragmentLookingBinding

    private val adapter by lazy {
        LookingSongRVAdapter()
    }

    val apiService by inject<ApiRepository>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentLookingBinding.inflate(inflater, container, false)
        .also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveSongRV.adapter = adapter
        lifecycleScope.launch {
            adapter.setList(apiService.getSongList()?: listOf())
        }
    }
}