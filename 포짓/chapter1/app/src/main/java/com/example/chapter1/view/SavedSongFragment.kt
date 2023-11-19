package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.R
import com.example.chapter1.adapter.SavedSongAdapter
import com.example.chapter1.databinding.FragmentSavedSongBinding
import com.example.chapter1.db.Song
import com.example.chapter1.db.SongDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SavedSongFragment : Fragment() {
    private lateinit var binding: FragmentSavedSongBinding
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDB
    val savedSongAdapter = SavedSongAdapter()

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
                binding.savedSongRv.layoutManager = LinearLayoutManager(requireContext())
                binding.savedSongRv.adapter = savedSongAdapter
                savedSongAdapter.setSelectListener {

                }
                savedSongAdapter.submitList(songs)
                savedSongAdapter.setCount(songs.size)
            }

        }

        binding.selectAll.setOnClickListener {
            val parent = requireActivity() as MainActivity
            when (binding.lockNav.visibility) {
                View.VISIBLE -> {
                    parent.setNavVisibility(View.VISIBLE)
                    savedSongAdapter.unselectAll()
                    binding.lockNav.visibility = View.GONE
                }

                View.GONE -> {
                    parent.setNavVisibility(View.GONE)
                    savedSongAdapter.selectAll()
                    binding.lockNav.visibility = View.VISIBLE
                }
            }
        }
        binding.lockNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.lock_delete -> {
                    updateLikeToDB()
                    val parent = requireActivity() as MainActivity
                    parent.setNavVisibility(View.VISIBLE)
                }
            }
            true
        }
    }

    private fun updateLikeToDB() {
        CoroutineScope(Dispatchers.IO).launch {
            CoroutineScope(Dispatchers.IO).async {
                val dao = songDB.songDao()
                songs.forEach {
                    dao.updateIsLike(it.id, false)
                }
            }.await()
            songs.clear()
            songs.addAll(songDB.songDao().getLikedSong(true))
            withContext(Dispatchers.Main) {
                savedSongAdapter.submitList(songs)
            }
        }
    }

    private fun initPlayList() {
        songDB = SongDB.getDB(requireContext())
        songs.addAll(songDB.songDao().getLikedSong(true))
    }

}