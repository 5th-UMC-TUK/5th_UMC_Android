package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.adapter.ChartAdapter
import com.example.chapter1.databinding.FragmentIncludedSongBinding
import com.example.chapter1.model.Song
import com.example.chapter1.network.SongRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class IncludedSongFragment(private val albumIdx: Int) : Fragment() {
    private lateinit var binding: FragmentIncludedSongBinding
    private val repository = SongRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIncludedSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initIncludingSongs()
    }

    private fun initIncludingSongs() {
        CoroutineScope(Dispatchers.IO).launch {
            val songs = CoroutineScope(Dispatchers.IO).async {
                repository.getAllSongs()
            }.await()
            songs?.let {
                if (it.code == 1000) {
                    withContext(Dispatchers.Main) {
                        val adapter = ChartAdapter()
                        binding.includedSongRv.adapter = adapter
                        binding.includedSongRv.layoutManager = LinearLayoutManager(requireContext())
                        val songs = arrayListOf<Song>()
                        it.result.songs.forEach { song ->
                            if (song.albumIdx == albumIdx) {
                                songs.add(song)
                            }
                        }
                        adapter.submitList(songs.toList())
                    }
                }
            }

        }
    }


}