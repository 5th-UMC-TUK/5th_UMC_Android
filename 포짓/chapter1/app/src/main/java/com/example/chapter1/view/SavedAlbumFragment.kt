package com.example.chapter1.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chapter1.R
import com.example.chapter1.adapter.SavedAlbumAdapter
import com.example.chapter1.databinding.FragmentSavedAlbumBinding
import com.example.chapter1.db.Album
import com.example.chapter1.db.SongDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SavedAlbumFragment : Fragment() {
    private lateinit var binding: FragmentSavedAlbumBinding
    val albums = arrayListOf<Album>()
    lateinit var songDB: SongDB
    val savedAlbumAdapter = SavedAlbumAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedAlbumBinding.inflate(layoutInflater, container, false)
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
                binding.savedAlbumRv.layoutManager = LinearLayoutManager(requireContext())
                binding.savedAlbumRv.adapter = savedAlbumAdapter
                savedAlbumAdapter.submitList(albums)
                savedAlbumAdapter.setCount(albums.size)
            }

        }

        binding.selectAll.setOnClickListener {
            val parent = requireActivity() as MainActivity
            when (binding.lockNav.visibility) {
                View.VISIBLE -> {
                    parent.setNavVisibility(View.VISIBLE)
                    savedAlbumAdapter.unselectAll()
                    binding.lockNav.visibility = View.GONE
                }

                View.GONE -> {
                    parent.setNavVisibility(View.GONE)
                    savedAlbumAdapter.selectAll()
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
                val dao = songDB.albumDao()
                albums.forEach {
                    dao.update(it)
                }
            }.await()
            albums.clear()
            albums.addAll(songDB.albumDao().getLikeAlbums(true))
            withContext(Dispatchers.Main) {
                savedAlbumAdapter.submitList(albums)
            }
        }
    }

    private fun initPlayList() {
        songDB = SongDB.getDB(requireContext())
        albums.addAll(songDB.albumDao().getLikeAlbums(true))
    }

}