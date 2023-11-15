package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.adapter.SavedSongAdapter
import com.example.chapter1.databinding.FragmentSavedSongBinding
import com.example.chapter1.db.Song
import com.example.chapter1.db.SongDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedSongBinding
    private lateinit var job: Job
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedSongBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            val deferred = CoroutineScope(Dispatchers.IO).async {
                initPlayList()
            }
            deferred.await()
            withContext(Dispatchers.Main) {
                val savedSongAdapter = SavedSongAdapter()
                binding.savedSongRv.layoutManager = LinearLayoutManager(requireContext())
                binding.savedSongRv.adapter = savedSongAdapter
                savedSongAdapter.submitList(songs)
                savedSongAdapter.setCount(songs.size)
            }

        }


    }

    private fun initPlayList() {
        songDB = SongDB.getDB(requireContext())
        songs.addAll(songDB.songDao().getLikedSong(true))
    }

}